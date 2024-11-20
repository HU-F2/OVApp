# Stapsgewijze instructie: Nieuwe taal toevoegen

1. **Maak een nieuw vertaalbestand**:
   - Kopieer een bestaand bestand, bijvoorbeeld `translation_nl.properties`, en hernoem dit naar `translation_xx.properties`, waarbij `xx` de taalcode is (bijvoorbeeld `translation_en.properties` voor Engels).

2. **Vul het nieuwe bestand met vertalingen**:
   - De keys van de vertalingen moeten overeenkomen met die in de andere bestanden. De tekst moet worden aangepast naar de nieuwe taal.
   - Voorbeeld:
     ```properties
     status.settings=Opening settings...
     error.api.parse=Something went wrong while processing the API: {0}.
     ```

3. **Voeg een nieuwe taal toe aan de configuratie**:
   - Voeg de nieuwe taal toe aan de `Language`-enum:
     ```java
     public enum Language {
        DUTCH("nl", "Nederlands"),
     }
     ```
   - Pas de taal van de applicatie aan:
     - **Globaal instellen**: Zet de taal in `GlobalConfig`:
       ```java
        // Standaard taal van de applicatie.
        // deze taal wordt gebruikt als de vertaling niet bestaat in de applicatie taal.
        public static final Language DEFAULT_LANGUAGE = Language.DUTCH;

        // Huidige taal (kan tijdens runtime veranderen)
        public static Language currentLanguage = DEFAULT_LANGUAGE;
       ```
     - **Runtime aanpassen**: voorbeeld code om van taal te veranderen:
       ```java
       GlobalConfig.setLanguage(Language.ENGLISH);
       ```

---

# Stapsgewijze instructie: Nieuwe tekst toevoegen (bestaande taal)

1. Open het juiste vertaalbestand, bijvoorbeeld `translation_nl.properties`.
2. Voeg een nieuwe regel toe:
   - **Zonder parameters**:
     ```properties
     status.newFeature=Nieuwe functie beschikbaar!
     ```
     Gebruik in de code:
     ```java
     String message = TranslationHelper.get("status.newFeature");
     ```

   - **Met parameters**:
     - Voeg placeholders `{0}`, `{1}`, enz. toe voor dynamische waarden:
       ```properties
       error.api.parse=Er ging iets mis bij het verwerken van de API: {0}.
       ```
     - Haal de tekst op en vervang de placeholders:
       ```java
       String message = TranslationHelper.get("error.api.parse", apiName);
       ```
     - **Uitleg placeholders**:
       - `{0}` wordt vervangen door de eerste parameter.
       - `{1}` door de tweede, enzovoorts.
       - Het aantal parameters is ongelimiteerd.
       - Volgorde en aantal placeholders is ongelimiteerd