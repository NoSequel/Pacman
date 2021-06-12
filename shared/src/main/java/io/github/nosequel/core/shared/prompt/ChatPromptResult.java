package io.github.nosequel.core.shared.prompt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatPromptResult {

    private final String format;
    private final boolean cancelled;

}
