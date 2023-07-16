package com.example.nlp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class App {
    public static void main(String[] args) {
        // Read the input text from a local file
        String filePath = "C:\\Users\\AVuser\\Downloads\\tech\\001.txt";
        String text;
        try {
            text = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
            return;
        }

        // Set up the Stanford NLP pipeline
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // Create an Annotation object
        Annotation document = new Annotation(text);

        // Process the document
        pipeline.annotate(document);

        // Get the list of sentences from the document
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        // Set of important named entity types
        Set<String> importantEntityTypes = new HashSet<>(Arrays.asList("LOCATION", "ORGANIZATION", "PERSON"));

        // Set of common words to filter out
        Set<String> commonWordsToFilter = new HashSet<>(Arrays.asList("the", "a", "an", "and", "or", "but", "of", "in", "on", "at", "to"));

        // Create a HashMap to store entity counts
        Map<String, Integer> entityCounts = new HashMap<>();

        // Iterate over the sentences
        for (CoreMap sentence : sentences) {
            // Get the list of tokens in the sentence
            List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);

            // Iterate over the tokens
            for (CoreLabel token : tokens) {
                // Get the named entity tag and word for the token
                String nerTag = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                String word = token.word().toLowerCase();

                // Check if the token is an important named entity and not a common word to filter
                if (!nerTag.equals("O") && importantEntityTypes.contains(nerTag) && !commonWordsToFilter.contains(word)) {
                    String entityValue = token.originalText();
                    entityCounts.put(entityValue, entityCounts.getOrDefault(entityValue, 0) + 1);
                }
            }
        }

        // Print the important named entities and their counts
        for (Map.Entry<String, Integer> entry : entityCounts.entrySet()) {
            System.out.println("Entity: " + entry.getKey() + "\tCount: " + entry.getValue());
        }
    }
}