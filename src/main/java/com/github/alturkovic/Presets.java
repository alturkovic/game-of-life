package com.github.alturkovic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Presets {
    public static boolean[][] loadPreset(final String presetName, final int size) {
        final boolean[][] preset = new boolean[size][size];

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(Presets.class.getClassLoader().getResource("presets/" + presetName).getFile()))) {
                int row = 0;
                String line;
                while ((line = br.readLine()) != null) {
                    char[] chars = line.toCharArray();
                    for (int col = 0; col < chars.length; col++) {
                        final char c = chars[col];
                        if (c == '1') {
                            preset[row][col] = true;
                        } else if (c == '0') {
                            preset[row][col] = false;
                        } else {
                            throw new IllegalArgumentException("Unexpected character '" + c + "' in preset: " + presetName);
                        }
                    }
                    row++;
                }
            }
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }

        return preset;
    }
}
