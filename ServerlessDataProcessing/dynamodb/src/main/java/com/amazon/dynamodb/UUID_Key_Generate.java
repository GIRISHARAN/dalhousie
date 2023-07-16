package com.amazon.dynamodb;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class UUID_Key_Generate {
    public static UUID generateUUIDKey() {

        /*There are four different versions of UUID. They are as follows:
        1. Time-based UUID
        2. DCE security UUID
        3. Name-based UUID
        4. Randomly generated UUID*/

        // for generating random UUID
        UUID uuidKey = UUID.randomUUID();

        // for generating Time-based UUID
        UUID uuidKey_timeBased = Generators.timeBasedGenerator().generate();
        return uuidKey_timeBased;
    }
}
