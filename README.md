# ResourcePAK

ResourcePAK is a fork of MagmaGuy's ResourcePackManager. It is a Minecraft server plugin that automates the merging, hosting, and conversion of resource packs for Java and Bedrock Edition (GeyserMC) clients.

## Fork Characteristics

- **Made for Core Shaders**: Specifically built and optimized for core shaders setups. It automatically deploys core shader files into the `blueprint` directory to ensure rendering logic.
- **No External Plugin Mixing**: All automatic integration and scanning of third-party plugin resource packs (such as Oraxen, Nexo, ModelEngine, etc.) has been removed to keep the mixing pipeline clean and focused.

## Key Enhancements in ResourcePAK

Compared to the upstream ResourcePackManager, this fork introduces the following enhancements:

- **Universal Java-to-Bedrock Conversion**: Converts 3D item models, custom armor, and modeled equipment for any plugin's resource pack (using 1.21.4+ items-definition packs), not just FreeMinecraftModels.
- **Pure-Java 3D Item Icon Renderer**: Correctly renders custom items in Bedrock inventories regardless of model rotation.
- **Dynamic Bedrock Session Serving**: Serves updated resource packs to joining Bedrock players via Geyser's API without requiring a Geyser server reboot.
- **Bedrock Display Offsets Config**: Includes `bedrock_display_offsets.yml` to tune first-person and third-person model rotation and translation offsets.
- **Custom Bedrock Armor**: Renders custom Java armor textures on Bedrock players using vanilla armor geometry.
- **Format-Aware Items Merging**: Properly parses and merges 1.21.4+ item model definitions, resolving layout conflicts and order issues.
- **Optimized Pipeline**: Immediately bails out on plugin disable, forcing in-flight AutoHost uploads and background tasks to close cleanly.

## Core Features

- **Resource Pack Merging (Mixer)**: Aggregates and merges resource packs from the `mixer` directory.
- **Conflict Resolution**: Resolves overlapping assets based on a configurable priority list.
- **Zero-Setup Hosting**: Hosts the merged resource pack automatically on remote hosting servers.
- **GeyserMC Integration**: Registers the converted Bedrock pack directly through Geyser's Pack Provider.

## Commands and Permissions

All commands use the base command `/resourcepackmanager` (alias `/rspm`) and require the permission `resourcepackmanager.*`.

- `/rspm reload` - Reloads all configuration files and triggers a full pack regeneration.
- `/rspm itemsadder <configure|dismiss>` - Configures the ItemsAdder integration by updating the ItemsAdder config (disabling its hosting and file protections) and reloading the plugins, or dismisses the ItemsAdder setup warning.
- `/rspm data_compliance_request` - Downloads a copy of all data stored on the auto-hoster associated with this server's unique ID.

## Configuration

### config.yml

- `priorityOrder`: Order in which resource packs resolve merge conflicts (highest priority on top).
- `autoHost`: Enables automatic hosting on remote servers.
- `forceResourcePack`: Determines whether clients are forced to accept the resource pack.
- `resourcePackPrompt`: Message prompt shown to players when asked to load the pack.
- `resourcePackRerouting`: Custom directory path relative to the plugins folder to copy the merged pack (if not hosting through AutoHost).
- `bedrockConversionEnabled`: Enables automated Java-to-Bedrock resource pack conversion.
- `bedrockAutoDeployToGeyser`: Automatically deploys the converted pack to the GeyserMC packs folder.
- `bedrockGeyserFolder`: The directory path to the GeyserMC packs folder. Auto-detected if left empty.

### bedrock_display_offsets.yml

Enables tuning of hand-held model positions and rotations on Bedrock Edition.
- **First-Person Offsets**: Adjusts the model rendering in the holder's view (X, Y, Z coordinates for rotation and position).
- **Third-Person Offsets**: Adjusts the model rendering when viewed by other players or in F5 mode.

## Installation

1. Place the `ResourcePAK.jar` in your server's `plugins` folder.
2. Restart the server.
3. Configure the resource pack priorities in `config.yml`.
4. If using **ItemsAdder**, run `/rspm itemsadder configure` to automatically align its settings and disable conflicting internal hosting and zip protections.

## Data Policy and Compliance

ResourcePAK includes an optional auto-host feature that temporarily hosts resource pack data on a remote server. 

- **Compliance**: Complies with Directive 2000/31/EC and Regulation (EU) 2022/2065.
- **Anonymity**: Assigns a random UUID to the server each time it reboots; no IP addresses or personally identifiable information are logged.
- **Retention**: Resource pack data and metadata are automatically deleted 24 hours after the server goes offline or fails to send its 6-hour keep-alive ping.
- **Access**: Server administrators can query all stored data at any time using the `/rspm data_compliance_request` command.
