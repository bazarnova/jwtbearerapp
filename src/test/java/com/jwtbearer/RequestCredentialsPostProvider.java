package com.jwtbearer;

import com.jwtbearer.model.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class RequestCredentialsPostProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        List<Arguments> requestCredentials = new ArrayList<>();
        for (Map<String, Object> map : testRequestCredentials()) {
            Map<String, Object> m = new HashMap<>(map);
            requestCredentials.add(Arguments.of(m));
        }
        return requestCredentials.stream();
    }

    private List<Map<String, Object>> testRequestCredentials() {
        return Arrays.asList(
                Map.ofEntries(
                        entry("user", new User("user1", "password1")),
                        entry("messageList", getMessageList("user1")),
                        entry("history", getCountOfReturningMessages()),
                        entry("expectedResp", getExpectedResponses())
                ),
                Map.ofEntries(
                        entry("user", new User("user2", "password2")),
                        entry("messageList", getMessageList("user2")),
                        entry("history", getCountOfReturningMessages()),
                        entry("expectedResp", getExpectedResponses())
                )
        );
    }

    public List<String> getMessageList(String name) {
        List<String> messageList = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            messageList.add(name + " sent message " + (i));
        }
        return messageList;
    }

    public List<String> getCountOfReturningMessages() {
        List<String> messageList = new ArrayList<>();
        for (int i = 5; i < 50; i += 5) {
            messageList.add("history " + i);
        }
        return messageList;
    }

    public List<Integer> getExpectedResponses() {
        return Stream
                .iterate(0, n -> n + 5)
                .limit(10)
                .collect(Collectors.toList());
    }
}
