package net.podiumfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.caffeinemc.mods.sodium.client.compatibility.checks.PreLaunchChecks;

/**
 * PodiumFix - Ersatz fuer "Podium", das Sodium 0.8.13's Launcher-Erkennung
 * (PojavLauncher/Amethyst) deaktiviert, damit Sodium nicht beim Start
 * abstuerzt.
 *
 * ===================== WICHTIG - VOR DEM BAUEN AUSFUELLEN =====================
 * Ersetze unten "METHODNAME_HIER_EINTRAGEN" mit dem echten Methodennamen,
 * den du in Schritt 1 der README.md per Decompiler gefunden hast.
 *
 * Beispiel: Wenn die dekompilierte Methode so aussieht:
 *
 *     private static boolean isRunningOnBadLauncher() { ... }
 *
 * dann setzt du unten:
 *
 *     method = "isRunningOnBadLauncher"
 *
 * Falls die Methode einen anderen Rueckgabetyp hat (nicht boolean) oder
 * Parameter hat, sag mir einfach die Zeile aus der dekompilierten Datei -
 * dann passe ich den Code exakt an.
 * ================================================================================
 */
@Mixin(PreLaunchChecks.class)
public class PreLaunchChecksMixin {

    // TODO: echten Methodennamen hier eintragen (siehe README.md Schritt 1)
    @Inject(method = "METHODNAME_HIER_EINTRAGEN", at = @At("HEAD"), cancellable = true)
    private static void podiumfix$disableLauncherCheck(CallbackInfoReturnable<Boolean> cir) {
        // Erzwingt "false" = "kein verbotener Launcher erkannt",
        // genau das, was Podium 1.1.0 auch getan hat.
        cir.setReturnValue(false);
    }
}
