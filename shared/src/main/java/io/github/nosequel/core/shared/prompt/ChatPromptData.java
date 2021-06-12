package io.github.nosequel.core.shared.prompt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ChatPromptData<T> {

    private final UUID target;
    private final ChatPrompt<T> prompt;
    private final T value;

}
