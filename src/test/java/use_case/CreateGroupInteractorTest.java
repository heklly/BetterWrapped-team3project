package use_case.creategroup;

import entity.User;

import org.junit.jupiter.api.Test;
import use_case.create_group.CreateGroupInputData;
import use_case.create_group.CreateGroupInteractor;
import use_case.create_group.CreateGroupOutputData;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateGroupInteractorTest {

    @Test
    void testCreatesGroupWithOnlyOwner() {
        User owner = new User("owner123","userowner", "June 8, 2024");

        CreateGroupInputData input = new CreateGroupInputData(
                "My Group", owner, null
        );

        CreateGroupInteractor interactor = new CreateGroupInteractor() {};
        CreateGroupOutputData output = interactor.execute(input);

        assertEquals("My Group", output.getGroup_name());
        assertEquals(owner, output.getOwner());
        assertEquals(1, output.getUsers().size());
        assertTrue(output.getUsers().contains(owner));
    }

    @Test
    void testCreatesGroupWithInitialMembers() {
        User owner = new User("U1", "u1id", "June 5, 2024");
        User u1 = new User("U2", "U2ID", "June 1, 2024");
        User u2 = new User("U3", "u3id","June 3, 2024");

        List<User> members = new ArrayList<>();
        members.add(u1);
        members.add(u2);

        CreateGroupInputData input = new CreateGroupInputData(
                "Better Wrapped Squad!", owner, members);

        CreateGroupInteractor interactor = new CreateGroupInteractor() {};
        CreateGroupOutputData output = interactor.execute(input);

        assertEquals("Better Wrapped Squad!", output.getGroup_name());
        assertEquals(owner, output.getOwner());
        assertEquals(3, output.getUsers().size());
        assertTrue(output.getUsers().contains(owner));
        assertTrue(output.getUsers().contains(u1));
        assertTrue(output.getUsers().contains(u2));
    }
}

