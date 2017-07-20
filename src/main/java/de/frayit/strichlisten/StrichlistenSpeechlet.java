package de.frayit.strichlisten;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;

import java.util.Objects;

public class StrichlistenSpeechlet implements SpeechletV2 {

    private Strichliste strichliste;

    public StrichlistenSpeechlet(Strichliste strichliste) {
        this.strichliste = Objects.requireNonNull(strichliste);
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        String intent = requestEnvelope.getRequest().getIntent().getName();
        if (intent.equals("GetStrichliste")) {
            return getStrichliste(requestEnvelope);
        } else if (intent.equals("UpdateStrichliste")) {
            return updateStrichliste(requestEnvelope);
        } else {
            SpeechletResponse response = new SpeechletResponse();
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Das hat ja gut funktioniert!");
            response.setOutputSpeech(speech);

            return response;
        }
    }

    private SpeechletResponse updateStrichliste(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        Slot name = requestEnvelope.getRequest().getIntent().getSlot("Name");

        strichliste.setzeStrichBei(name.getValue());

        Integer striche = strichliste.stricheVon(name.getValue());

        SpeechletResponse response = new SpeechletResponse();
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(name + " hat jetzt " + striche + " auf der Strichliste");
        response.setOutputSpeech(speech);

        return response;
    }

    private SpeechletResponse getStrichliste(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        Slot name = requestEnvelope.getRequest().getIntent().getSlot("Name");

        if (name == null) {
            Integer striche = strichliste.alleStriche();

            SpeechletResponse response = new SpeechletResponse();
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Es sind " + striche + " auf der Strichliste");
            response.setOutputSpeech(speech);

            return response;
        } else {
            Integer striche = strichliste.stricheVon(name.getValue());

            SpeechletResponse response = new SpeechletResponse();
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText(name + " hat " + striche + " auf der Strichliste");
            response.setOutputSpeech(speech);

            return response;
        }
    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        SpeechletResponse response = new SpeechletResponse();
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Das Event onLaunch wurde ausgeführt.");
        response.setOutputSpeech(speech);

        return response;
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {

    }

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {

    }

}
