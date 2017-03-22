package com.example.android.mockitoasync;


import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DummyCollaboratorCallerTest {
    // Class under test
    private DummyCaller dummyCaller;

    @Mock
    private DummyCollaborator mockDummyCollaborator;

    @Captor
    private ArgumentCaptor<DummyCallback> dummyCallbackArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dummyCaller = new DummyCaller(mockDummyCollaborator);
    }

    //@Test
    public void testDoSomethingAsynchronouslyUsingDoAnswer() {
        final List<String> results = Arrays.asList("One", "Two", "Three");

        // Let's do a synchronous answer for the callback
        /* when mockDummyCollaborator.doSomethingAsynchronously() is called with any DummyCallback
         * imidiatelly execute a success callback with prepared data  */
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                // imidiatelly execute a callback
                ((DummyCallback)invocation.getArguments()[0]).onSuccess(results);
                return null;
            }
        }).when(mockDummyCollaborator).doSomethingAsynchronously(
                any(DummyCallback.class));

        // Let's call the method under test
        dummyCaller.doSomethingAsynchronously();

        // Verify state and interaction
        verify(mockDummyCollaborator, times(1)).doSomethingAsynchronously(
                any(DummyCallback.class));
        assertThat(dummyCaller.getResult(), is(equalTo(results)));
    }

    @Test
    public void testDoSomethingAsynchronouslyUsingArgumentCaptor() {
        // Let's call the method under test
        dummyCaller.doSomethingAsynchronously();

        final List<String> results = Arrays.asList("One", "Two", "Three");

        // Let's call the callback. ArgumentCaptor.capture() works like a matcher.
        verify(mockDummyCollaborator, times(1)).doSomethingAsynchronously(
                dummyCallbackArgumentCaptor.capture());

        // Some assertion about the state before the callback is called
        assertThat(dummyCaller.getResult().isEmpty(), is(true));

        // Once you're satisfied, trigger the reply on callbackCaptor.getValue().
        dummyCallbackArgumentCaptor.getValue().onSuccess(results);

        // Some assertion about the state after the callback is called
        assertThat(dummyCaller.getResult(), is(equalTo(results)));
    }
}
