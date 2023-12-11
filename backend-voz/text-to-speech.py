from flask import Flask, request, jsonify
import pyttsx3
import threading

app = Flask(__name__)

def text_to_speech(message):
    try:
        engine = pyttsx3.init()
        voices = engine.getProperty('voices')
        engine.setProperty('voice', voices[0].id)  # Elige la primera voz disponible
        engine.say(message)
        engine.runAndWait()
        engine.stop()
    except Exception as e:
        print(str(e))

@app.route('/generate-voice', methods=['POST'])
def generate_voice():
    try:
        data = request.get_json()
        message = data.get('message')

        if not message:
            return jsonify({'error': 'Message is required'}), 400

        # Ejecuta text_to_speech() en un hilo separado
        threading.Thread(target=text_to_speech, args=(message,)).start()

        return jsonify({'status': 'Success'})
    except Exception as e:
        print(str(e))
        return jsonify({'error': 'Internal Server Error'}), 500

if __name__ == '__main__':
    app.run(port=4000)
