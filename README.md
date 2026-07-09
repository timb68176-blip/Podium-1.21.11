# PodiumFix – ein selbstgebauter Podium-Ersatz für Sodium 0.8.13 (MC 1.21.11)

Warum du das selbst bauen musst: Um diesen Mod zu kompilieren, lädt Gradle
automatisch die echten Minecraft-, Fabric- und Sodium-Bibliotheken aus dem
Internet herunter. Das kann eine KI-Sandbox ohne Internetzugang nicht tun –
du brauchst dafür einen PC mit Internetverbindung, Java (JDK 21) und ein
paar Minuten Zeit.

Dieses Paket enthält den kompletten, fertigen Quellcode. Du musst nur EINEN
Wert ausfüllen (den genauen internen Methodennamen), dann bauen.

---

## SCHRITT 1: Den echten Methodennamen finden (2 Minuten, nur Java nötig)

Podium 1.1.0 ist genau deswegen abgestürzt, weil es eine Methode namens
`isUsingPrismLauncher()` in Sodiums Klasse `PreLaunchChecks` sucht, die es
in deiner Sodium-Version (0.8.13) so nicht mehr gibt (Sodium hat den Code
seitdem intern umgebaut). Wir müssen den NEUEN Namen dieser Methode finden.

1. Lade dir das kostenlose Decompiler-Tool CFR herunter:
   https://www.benf.org/other/cfr/ (Datei: `cfr-<version>.jar`)

2. Nimm deine installierte Sodium-Datei, z. B.
   `sodium-fabric-0.8.13+mc1.21.11.jar`

3. Führe im Terminal (Windows: cmd/PowerShell, Linux/Mac: Terminal) aus:

   ```
   java -jar cfr-<version>.jar sodium-fabric-0.8.13+mc1.21.11.jar
     --outputdir sodium_decompiled
   ```

4. Öffne die Datei:
   `sodium_decompiled/net/caffeinemc/mods/sodium/client/compatibility/checks/PreLaunchChecks.java`

5. Suche nach Text wie `"pojav"`, `"amethyst"`, `"prism"` oder
   `"launcher"` (Groß-/Kleinschreibung egal). Du wirst eine Methode finden,
   die einen `boolean` zurückgibt und den Launcher-Namen prüft – das ist
   unser Ziel. Notiere dir:
   - den genauen Methodennamen (z. B. `checkForBadLauncher` o.ä.)
   - ob sie Parameter hat (meistens keine)

6. Trag den Namen in `PreLaunchChecksMixin.java` ein (siehe `TODO` dort).

Falls du dir unsicher bist, schick mir einfach die paar Zeilen aus der
dekompilierten Datei rund um den Fund – dann passe ich dir den Mixin-Code
exakt an.

---

## SCHRITT 2: Mod bauen

Voraussetzung: Java 21 (JDK) installiert, Internetverbindung.

**Wichtig:** Dieses Paket enthält NICHT den Gradle-Wrapper (`gradlew`,
`gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar`), weil das binäre Dateien
sind, die ich hier nicht erzeugen kann. So bekommst du sie einfach dazu:

1. Lade das offizielle Fabric-Mod-Vorlage-Projekt herunter:
   https://github.com/FabricMC/fabric-example-mod (grüner "Code"-Button →
   "Download ZIP")
2. Entpacke es, und kopiere daraus NUR diese Dateien/Ordner in diesen
   `podiumfix`-Ordner (überschreiben erlaubt):
   - `gradlew`
   - `gradlew.bat`
   - `gradle/` (kompletter Ordner, enthält den Wrapper)
3. Jetzt hast du ein komplettes, baubares Projekt.
4. Öffne ein Terminal in diesem Ordner (`podiumfix/`)
5. Linux/Mac: `./gradlew build`
   Windows: `gradlew.bat build`
6. Gradle lädt beim ersten Mal automatisch alles Nötige herunter
   (Fabric Loom, Minecraft, Mappings, Sodium) – das dauert etwas.
7. Die fertige Datei liegt danach in:
   `build/libs/podiumfix-1.0.0.jar`
8. Diese Datei in deinen `mods`-Ordner legen, neben Sodium.

**Hinweis zu den Versionsnummern in `gradle.properties` / `build.gradle`:**
Die Werte für `yarn_mappings` und `fabric_version` sind meine beste
Einschätzung basierend auf dem, was ich über 1.21.11 finden konnte – ich
konnte sie nicht live gegen den Fabric-Server verifizieren (kein Internet
in meiner Umgebung). Falls Gradle beim Bauen einen Fehler wie
"Could not find net.fabricmc:yarn:..." wirft: geh auf
https://fabricmc.net/develop/ oder https://modrinth.com/mod/fabric-api
und trag die dort angezeigte aktuelle Versionsnummer für 1.21.11 ein.

---

## Was der Mod macht

Genau wie Podium: Er schaltet per Mixin die Launcher-Erkennungsprüfung in
Sodium aus, damit Sodium nicht wegen "falscher Launcher" (PojavLauncher/
Amethyst) abstürzt.

## Wenn der Build fehlschlägt

Der häufigste Fehler: falscher Methodenname in Schritt 1. Schick mir die
Fehlermeldung (sie nennt genau, welche Methode nicht gefunden wurde), dann
korrigiere ich den Code für dich.
