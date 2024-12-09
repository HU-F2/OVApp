## Uitleg Commit Types
1. **build**  
   Voor veranderingen die het buildproces of afhankelijkheden beïnvloeden (bijvoorbeeld Maven, Gradle, npm).
   - **Voorbeelden**:
     - `build: update Maven pom.xml dependencies`
     - `build: add Webpack configuration`

2. **chore**  
   Voor kleine wijzigingen die geen invloed hebben op de functionaliteit of het gedrag van de code (zoals onderhoudstaken).
   - **Voorbeelden**:
     - `chore: update .gitignore`
     - `chore: clean up unused files`

3. **ci**  
   Voor wijzigingen aan de continuous integration (CI) configuratiebestanden en scripts.
   - **Voorbeelden**:
     - `ci: update GitHub Actions workflow`
     - `ci: fix Jenkins pipeline error`

4. **docs**  
   Voor documentatie-updates (README.md, inline comments, enz.).
   - **Voorbeelden**:
     - `docs: update API documentation`
     - `docs: add examples to README`

5. **feat**  
   Voor het toevoegen van een nieuwe functionaliteit.
   - **Voorbeelden**:
     - `feat: implement user login`
     - `feat: add support for dark mode`

6. **fix**  
   Voor bugfixes.
   - **Voorbeelden**:
     - `fix: resolve null pointer exception`
     - `fix: correct typo in API response`

7. **perf**  
   Voor prestatieverbeteringen zonder wijzigingen in de functionaliteit.
   - **Voorbeelden**:
     - `perf: optimize database queries`
     - `perf: reduce image loading time`

8. **refactor**  
   Voor wijzigingen in de code die geen nieuwe functionaliteit toevoegen of bugs oplossen, maar de code verbeteren.
   - **Voorbeelden**:
     - `refactor: simplify validation logic`
     - `refactor: split service into smaller modules`

9. **revert**  
   Voor het ongedaan maken van een eerdere commit.
   - **Voorbeelden**:
     - `revert: remove previous broken commit`

10. **style**  
    Voor wijzigingen die alleen betrekking hebben op opmaak (zoals indents, spaties, komma's) en geen invloed hebben op de functionaliteit.
    - **Voorbeelden**:
      - `style: fix linting errors`
      - `style: format codebase`

11. **test**  
    Voor het toevoegen of aanpassen van tests.
    - **Voorbeelden**:
      - `test: add unit tests for service class`
      - `test: fix broken integration tests`