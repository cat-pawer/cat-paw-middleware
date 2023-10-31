package com.catpaw.catpawmiddleware.repository.friend;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendCustomRepository {

    private final EntityManager em;
}
