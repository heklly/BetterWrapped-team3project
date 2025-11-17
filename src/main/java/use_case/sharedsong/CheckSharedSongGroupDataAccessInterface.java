package use_case.sharedsong;

import entity.Group;
import entity.User;

/**
 * DAO Interface for the Check Shared Song Use Case.
 */
public interface CheckSharedSongGroupDataAccessInterface {
    /**
     * @param user
     * @param group
     */
    void checkSharedSong(User user, Group group);
}
