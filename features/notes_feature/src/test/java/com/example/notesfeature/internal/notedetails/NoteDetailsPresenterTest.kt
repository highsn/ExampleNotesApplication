package com.example.notesfeature.internal.notedetails

import com.example.notesfeature.internal.notedetail.NoteDetailsNavigation
import com.example.notesfeature.internal.notedetail.NoteDetailsPresenter
import com.example.notesfeature.internal.notedetail.NoteDetailsViewContainer
import com.example.notesfeature.internal.notedetail.NoteState
import com.example.notesfeature.internal.service.Note
import com.example.notesfeature.internal.service.NoteService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class NoteDetailsPresenterTest {

    private val note = Note(1, "Title", "Body")
    private val notes: List<Note> = listOf(note)
    private val scope: CoroutineScope = TestCoroutineScope()

    /**
     * ADR # 17. Testing: use Mockito MockMaker for mocking in unit tests
     */
    private val view: NoteDetailsViewContainer = mock()
    private val navigation: NoteDetailsNavigation = mock()
    private val service: NoteService = mock()
    private val sut = NoteDetailsPresenter(view, navigation, service, note.id, scope)

    @Test
    fun `after initialization note will be loaded`() = runBlocking {
        whenever(service.getNote(note.id)).thenReturn(note)

        sut.start()

        verify(view).setLoading(true)
        verify(service).getNote(note.id)
        verify(view).showNote(NoteState.SingleNote(note))
    }

    @Test
    fun `when note is selected details should open`() = runBlocking {
        whenever(view.note.value).thenReturn(NoteState.SingleNote(note))

        sut.onCloseNoteSelected()

        verify(navigation).closeNoteDetails(note)
    }

}
