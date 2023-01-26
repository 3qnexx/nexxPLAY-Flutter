/// nexxPLAY plugin provides a widget that can be used to embed the native
/// Android player view into Flutter widget hierarchy, configuration routines,
/// controller to manage the player, and the event system that can be plugged
/// into with subscribers.
///
/// Refer to the documentation of the corresponding class if you are interested
/// in certain specifics.

library nexxplay;

export 'src/configuration.dart' show NexxPlayConfiguration, NexxPlayEnvironment;
export 'src/controller.dart' show NexxPlayController, NexxPlayControllerFactory;
export 'src/details.dart'
    show
        AudioTrack,
        ConnectedFile,
        MediaData,
        MediaParentData,
        CurrentPlaybackState,
        MediaGeneral,
        OfflineMediaResult;
export 'src/event.dart'
    show
        DirectPlayerEvent,
        PlayerEvent,
        PlayerEventVisitor,
        PlayerStateChangeEvent,
        NexxEventType,
        PlayerState,
        AdHocVisitor;
export 'src/widget.dart' show NexxPlay, NexxPlayState;
