Fortune Shears (NeoForge 1.21.1)

Shears accept the Fortune enchantment (enchanting table, anvil, books). When you shear a sheep with fortune shears you get the normal wool plus bonus wool: woolPerLevel * (level + random 0..level). With the default woolPerLevel of 1: Fortune I +1-2, III +3-6, V +5-10, X +10-20. Tune it in config/fortuneshears-common.toml.

Fortune max level is raised from 3 to 10 for every tool by data/minecraft/enchantment/fortune.json. Edit max_level there to change it. The enchanting table only reaches III; higher levels come from combining two equal books or items on an anvil, or from commands.

Build: copy gradlew, gradlew.bat and gradle/wrapper/gradle-wrapper.jar from another NeoForge project, then run ./gradlew build. The jar lands in build/libs.
