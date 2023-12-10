import cv2
import numpy as np
from collections import Counter
import requests  # Importa el módulo requests

mensajes_impresos = set()
nameWindow = "Calculadora"
GRID_ROWS = 3
GRID_COLS = 3
url = 'http://localhost:3000/'  # Reemplaza esto con la URL correcta


def nothing(x):
    pass

def constructorVentana():
    cv2.namedWindow(nameWindow)
    cv2.createTrackbar("min", nameWindow, 0, 255, nothing)
    cv2.createTrackbar("max", nameWindow, 100, 255, nothing)
    cv2.createTrackbar("kernel", nameWindow, 1, 100, nothing)
    cv2.createTrackbar("areaMin", nameWindow, 500, 10000, nothing)

def calcularAreas(figuras):
    areas = []
    for figuraActual in figuras:
        areas.append(cv2.contourArea(figuraActual))
    return areas

def dibujarCuadricula(imagen):
    altura, ancho, _ = imagen.shape

    # Dibujar líneas verticales
    for i in range(1, GRID_COLS):
        x = int(i * ancho / GRID_COLS)
        cv2.line(imagen, (x, 0), (x, altura), (255, 255, 255), 2)

    # Dibujar líneas horizontales
    for i in range(1, GRID_ROWS):
        y = int(i * altura / GRID_ROWS)
        cv2.line(imagen, (0, y), (ancho, y), (255, 255, 255), 2)

def detectarFigura(imagenOriginal):
    imagenGris = cv2.cvtColor(imagenOriginal, cv2.COLOR_BGR2GRAY)
    min = cv2.getTrackbarPos("min", nameWindow)
    max = cv2.getTrackbarPos("max", nameWindow)
    bordes = cv2.Canny(imagenGris, min, max)
    tamañoKernel = cv2.getTrackbarPos("kernel", nameWindow)
    kernel = np.ones((tamañoKernel, tamañoKernel), np.uint8)
    bordes = cv2.dilate(bordes, kernel)
    figuras, _ = cv2.findContours(bordes, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    areas = calcularAreas(figuras)
    i = 0
    areaMin = cv2.getTrackbarPos("areaMin", nameWindow)
    mensajes = set()
    for figuraActual in figuras:
        if areas[i] >= areaMin:
            vertices = cv2.approxPolyDP(figuraActual, 0.05 * cv2.arcLength(figuraActual, True), True)
            if len(vertices) == 3 or len(vertices) == 4:
                x, y, w, h = cv2.boundingRect(figuraActual)
                centro_x = x + w // 2
                centro_y = y + h // 2
                cuadro_x = centro_x // (imagenOriginal.shape[1] // GRID_COLS) + 1
                cuadro_y = centro_y // (imagenOriginal.shape[0] // GRID_ROWS) + 1

                tipo_figura = "Triángulo" if len(vertices) == 3 else "Cuadrado"
                mensaje = f"{tipo_figura} {cuadro_x},{cuadro_y}"

                cv2.drawContours(imagenOriginal, [figuraActual], 0, (0, 0, 255), 2)
                mensajes.add(mensaje)

        i += 1

    dibujarCuadricula(imagenOriginal)
    cv2.imshow("Imagen", imagenOriginal)

    return mensajes

def convertir_a_matriz(datos):
    matriz = [[' ' for _ in range(3)] for _ in range(3)]

    for dato in datos:
        tipo, posicion = dato.split()
        fila, columna = map(int, posicion.split(','))

        # Ajustar la numeración de fila y columna a índices de lista
        fila -= 1
        columna -= 1

        if tipo == 'Cuadrado':
            matriz[columna][fila] = 'C'
        elif tipo == 'Triángulo':
            matriz[columna][fila] = 'T'

    return matriz

def mostrar_matriz(matriz):
    for fila in matriz:
        print(fila)

def imageProcess(image):
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    blurred = cv2.GaussianBlur(gray, (5, 5), 0)
    edges = cv2.Canny(blurred, threshold1=100, threshold2=200)
    kernel = np.ones((5, 5), np.uint8)
    edges = cv2.dilate(edges, kernel, iterations=1)
    contours, _ = cv2.findContours(edges, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    inner_squares = []
    counter = 0

    for i, contour in enumerate(contours):
        if cv2.contourArea(contour) > 1000:
            counter += 1
            x, y, w, h = cv2.boundingRect(contour)
            if 0 <= y + 10 < y + h - 10 <= image.shape[0] and 0 <= x + 10 < x + w - 10 <= image.shape[1]:
                inner_squares.append(contour)
                square = image[y + 10:y + h - 10, x + 10:x + w - 10]
                cv2.imwrite(f'recortes/inner_square_{counter}.jpg', square)

    cv2.drawContours(image, inner_squares, -1, (0, 255, 0), 3)
    return image, edges

# Inicializar la captura de video desde la cámara
cap = cv2.VideoCapture("http://192.168.1.75:4747/video")
constructorVentana()

# Inicializa el contador y el conjunto para mensajes
contador_repeticiones = 0
mensajes_impresos = set()

while True:
    ret, frame = cap.read()
    frame, edges = imageProcess(frame)
    cv2.imshow('edges', edges)
    nuevos_mensajes = detectarFigura(frame)

    # Verifica si el mensaje ya ha sido impreso
    if str(nuevos_mensajes) not in mensajes_impresos:
        # Si no, agrégalo al conjunto y reinicia el contador
        mensajes_impresos.add(str(nuevos_mensajes))
        contador_repeticiones = 0

       
    else:
        # Si el mensaje ya está en el conjunto, incrementa el contador
        contador_repeticiones += 1

    # Si el mensaje ha sido repetido x veces, imprimirlo
    if contador_repeticiones == 100:
        print(str(nuevos_mensajes))
        matriz_resultante = convertir_a_matriz(nuevos_mensajes)
        mostrar_matriz(matriz_resultante)
         # Envia nuevos_mensajes al servidor mediante una solicitud POST
        try:
            response = requests.post(url, json={'mensajes': list(matriz_resultante)})
            #response = requests.post(url, json={'mensajes': list(matriz_resultante)})
            if response.status_code == 200:
                print("Datos enviados al servidor con éxito")
            else:
                print(f"Error al enviar datos al servidor. Código de estado: {response.status_code}")
        except Exception as e:
            print(f"Error en la solicitud POST: {e}")

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()