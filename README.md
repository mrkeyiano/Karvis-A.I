# Karvis-A.I

* Karvis Artificial Inteligence Program is a voice recognition program that intercepts speeches and executes commands
based on voice input.

* Karvis-A.I makes use of Google speech to text api.
* Karvis-A.I is built in java


Features:

The API currently provides the following functionality,

* Microphone Capture API (Wrapped around the current Java API for simplicity)
* A speech recognizer using Google's recognizer service
* Converts WAVE files from microphone input to FLAC 
* Retrieves Response from Google, including confidence score and text
* A speech synthesiser using Google's synthesizer service
* Retrieves synthesized text in an InputStream (MP3 data ready to be played)
* Wave to FLAC API (Wrapped around the used API in the project, javaFlacEncoder)

# Notes:

* To get access to the Google API, you need an API key. To get this, you need to follow the instructions here:

* https://stackoverflow.com/questions/26485531/google-speech-api-v2

* needs ffpmpeg running in command line https://www.ffmpeg.org/download.html
* requires internet connection


FUTURE UPGRADES:

* User interface needs to be improved upon.
* integration of chat bot i.e Alice



