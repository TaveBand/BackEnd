package ys_band.develop.service;


import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class PitchService {

    public List<Float> extractPitches(String filePath) throws Exception {
        final List<Float> pitches = new ArrayList<>();
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromFile(new File(filePath), 2048, 1024);
        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult result, AudioEvent e) {
                if (result.getPitch() != -1) {
                    pitches.add(result.getPitch());
                }
            }
        };

        dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN, 44100, 2048, pdh));
        dispatcher.run();

        return pitches;
    }

    public float[] getMinMaxPitches(List<Float> pitches) {
        float minPitch = Float.MAX_VALUE;
        float maxPitch = Float.MIN_VALUE;

        for (float pitch : pitches) {
            if (pitch < minPitch) {
                minPitch = pitch;
            }
            if (pitch > maxPitch) {
                maxPitch = pitch;
            }
        }

        return new float[]{minPitch, maxPitch};
    }

    public String getRangeFromPitches(float minPitch, float maxPitch) {
        if (maxPitch <= 87.31) { // F2
            return "low";
        } else if (maxPitch <= 110.00) { // A2
            return "mid";
        } else if (maxPitch <= 130.81) { // C3
            return "high";
        } else if (maxPitch <= 164.81) { // E3
            return "very_high";
        } else {
            return "ultra_high";
        }
    }
}
