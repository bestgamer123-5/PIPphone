# PIPphone (Minecraft 1.21.1 Fabric Mod)

In-game phone HUD and app menu integrated with **CarLib** for Minecraft 1.21.1.

## Requirements

- **CarLib** — sibling project at `../CarLib` (included in Gradle settings)
- **Fabric Loader** `0.16.14`, **Fabric API**, **Java 21**

## Versions

| Component | Version |
|-----------|---------|
| Minecraft | `1.21.1` |
| Yarn | `1.21.1+build.3` |
| Fabric Loader | `0.16.14` |
| Fabric Loom | `1.8.13` |
| Fabric API | `0.116.12+1.21.1` |

## Build and run

Build CarLib first (Gradle composite project):

```bash
cd ../CarLib && ./gradlew build
cd ../PIPphone
./gradlew build
./gradlew runClient
```

## In-game usage

| Action | Result |
|--------|--------|
| HUD (top-right) | Time, speed, gear, engine, fuel, battery |
| **P** key | Opens PIPphone app menu |
| App buttons | Chat message confirming the app (placeholder) |

Telemetry is resolved in this order:

1. Other mods’ `CarLibApi` providers exposing the `telemetry` feature
2. Reflection on the vehicle you are riding (`getSpeedKmh`, etc.)
3. Player movement fallback when driving/riding

## CarLib integration

On startup, **PIPphone** registers:

- `CarLibApi.registerProvider(PipphoneCarProvider)` — telemetry feature for other mods
- `UiApi.registerHudOverlay(PipphoneHudOverlay)` — phone HUD contract
- `UiApi.registerMenu(...)` — app menu definition (buttons + progress elements)

Rendering is done by PIPphone (CarLib stores registrations; it does not draw the HUD itself).

## Entry points

| Side | Class |
|------|--------|
| Common | `com.pipphone.mod.PipphoneMod` |
| Client | `com.pipphone.client.PipphoneClientMod` |

## Optional desktop demo

```bash
./gradlew build
java -cp "build/devlibs/pipphone-1.0.0-dev.jar:../CarLib/build/devlibs/carlib-0.1.0-dev.jar" com.pipphone.Main
```

## Project layout

```
src/main/java/com/pipphone/
  mod/          Fabric common init
  client/       HUD, screen, keybind, CarLib UI registration
  phone/        Controller, telemetry, apps
  integration/  PipphoneCarProvider
```

## Local CarLib vs JitPack

Gradle uses the **local** CarLib project (`settings.gradle` includes `../CarLib`). To use JitPack instead, remove the `include 'carlib'` block and set:

```gradle
modImplementation "com.github.bestgamer123-5:CarLib:${carlib_ref}"
```
