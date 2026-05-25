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
