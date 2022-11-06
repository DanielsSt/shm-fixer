### Xposed Module to fix Samsung Health Monitor app crash (on non-Samsung devices?)
#### Requirements
* Xposed (tested with LSPosed + Zygisk)
* Perhaps some Magisk modules to hide root are needed as well, have not tried without them

Samsung Health Monitor app on my Asus Zenfone was crashing right after accepting ToS

Fixed the crash by hooking `startLibrary` method and calling `callFinishAndGoMain` right after it + noticed that the app did not like my location (`LV`), spoofed it to `US`, everything seems to be working _(at least ECG, have not calibrated BP, but that seems to be working)_

Maybe someone will find this useful