package ys_band.develop.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@Service
public class AudioConversionService {

    private static final Logger logger = Logger.getLogger(AudioConversionService.class.getName());

    public File convertToWav(File source) throws IOException {
        // Check if the file is already a WAV file
        if (source.getName().endsWith(".wav")) {
            return source;
        }

        File target = new File(source.getParent(), source.getName().replaceFirst("[.][^.]+$", "") + ".wav");

        // Path to the ffmpeg executable
        String ffmpegPath = "C:\\ffmpeg\\bin\\ffmpeg.exe";

        // Construct the ffmpeg command
        String command = String.format("%s -i \"%s\" -acodec pcm_s16le \"%s\"", ffmpegPath, source.getAbsolutePath(), target.getAbsolutePath());

        // Execute the command
        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Audio conversion interrupted", e);
        } catch (IOException e) {
            logger.severe("Failed to convert audio file: " + e.getMessage());
            throw new IOException("Failed to convert audio file", e);
        }

        return target;
    }
}
