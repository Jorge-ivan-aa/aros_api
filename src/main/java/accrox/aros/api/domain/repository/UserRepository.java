package accrox.aros.api.domain.repository;

import java.util.List;
import java.util.Optional;

import accrox.aros.api.domain.model.User;

public interface UserRepository {

    /**
     * Finds a user by its unique identifier.
     *
     * @param id the unique ID of the user.
     * @return an {@link Optional} containing the {@link User} if found, or empty if
     *         not present.
     */
    Optional<User> findById(Long id);

    /**
     * Finds a user by their email address.
     *
     * @param username the email or username used for identification.
     * @return an {@link Optional} containing the {@link User} if found, or empty if
     *         not present.
     */
    Optional<User> findByEmail(String username);

    /**
     * Finds a user by their document number.
     *
     * @param document the user's document number (e.g., national ID or passport).
     * @return an {@link Optional} containing the {@link User} if found, or empty if
     *         not present.
     */
    Optional<User> findByDocument(String document);

    /**
     * @return
     */
    List<User> findAll();

    /**
     * Persists a user in the underlying data source.
     * If the user already exists, it will be updated; otherwise, it will be
     * created.
     *
     * @param user the {@link User} entity to be saved.
     */
    void save(User user);

  //  void updateUserArea(User user , Collection<AreaEntity> areas);


    void update(User user);

    /**
     * Deletes a user from the data source using their document number.
     *
     * @param document the document number identifying the user to delete.
     */
    void deleteUserByDocument(String document);

}