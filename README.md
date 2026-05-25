# PIPphone (Minecraft 1.21.1 Fabric Mod)

This repository is now configured as a **Minecraft 1.21.1 Fabric mod** project, instead of a standalone Swing app.

## Versions

- Minecraft: `1.21.1`
- Yarn mappings: `1.21.1+build.3`
- Fabric Loader: `0.16.14`
- Fabric API: `0.116.12+1.21.1`

## CarLib integration

The project keeps CarLib as a dependency:

```gradle
modImplementation 'com.github.bestgamer123-5:CarLib:main-SNAPSHOT'
```

This allows implementing `HudOverlay`, `MenuScreenDefinition`, `ButtonElement`, `ProgressBarElement`, `RpgInterfaceDefinition`, and `UiApi` registration inside a proper 1.21.1 mod runtime.

## Build / Run

```bash
./gradlew build
./gradlew runClient
```

## Entry point

Main mod initializer:
- `com.pipphone.mod.PipphoneMod`

## CarLib status recheck (May 25, 2026)

Rechecked upstream: CarLib now targets **Minecraft 1.21.1** on `main` with:
- `minecraft_version=1.21.1`
- `yarn_mappings=1.21.1+build.3`
- `loader_version=0.16.14`
- `fabric_version=0.116.12+1.21.1`

`carlib_ref` remains configurable for pinning to commit/tag if needed.
# PIPphone (CarLib HUD + App Phone UI)

A Java phone-style GUI/HUD that consumes telemetry from `CarLib` and also includes app buttons (Navigation, Radio, Camera, Messages, Garage, Weather, Calls, Settings).

## Why this design

CarLib is a general-purpose Fabric interoperability library (not only for cars). This project reflects that by combining:
- a vehicle HUD panel (speed/gear/engine/fuel/battery), and
- a phone app launcher surface (GUI/UI app grid).

## CarLib dependency

```gradle
implementation 'com.github.bestgamer123-5:CarLib:main-SNAPSHOT'
```

## Run

```bash
javac $(find src/main/java -name '*.java')
java -cp src/main/java com.pipphone.Main
```

## Integrate a real CarLib object

Pass your runtime CarLib-backed object to:

```java
new ReflectionCarLibTelemetryProvider(carLibVehicleInstance)
```

Expected methods (if present):
- `getSpeedKmh()`
- `getFuelPercent()`
- `getBatteryPercent()`
- `getGear()`
- `isEngineOn()`


## CarLib UI API bridge support

This project now includes a reflection bridge for CarLib UI entities often used in mods:
- `HudOverlay`
- `MenuScreenDefinition`
- `ButtonElement`
- `ProgressBarElement`
- `RpgInterfaceDefinition`
- `UiApi` (register overlay/menu/RPG interfaces)

`CarLibUiBridge` now mirrors the current CarLib package (`com.carlib.api.ui`) and registers:
- `UiApi.registerHudOverlay(HudOverlay)`
- `UiApi.registerMenu(MenuScreenDefinition)`

It also constructs `RpgInterfaceDefinition` for compatibility with RPG-style UI bundles in CarLib-based integrations.
