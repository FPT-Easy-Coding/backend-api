package com.quiztoast.backend_api.util;

import java.security.SecureRandom;

public class ClassroomSlugGenerator {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final int GROUP_LENGTH = 3;
    private static final int GROUP_COUNT = 3;
    private static final String SEPARATOR = "-";

    public static String generateSlug() {
        StringBuilder sb = new StringBuilder();

        // Generate three groups
        for (int i = 0; i < GROUP_COUNT; i++) {
            // Generate each group
            for (int j = 0; j < GROUP_LENGTH; j++) {
                sb.append(CHARACTERS.charAt(new SecureRandom().nextInt(CHARACTERS.length())));
            }
            // Add separator between groups except the last one
            if (i < GROUP_COUNT - 1) {
                sb.append(SEPARATOR);
            }
        }

        return sb.toString();
    }

}
