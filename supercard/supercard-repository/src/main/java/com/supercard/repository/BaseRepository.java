package com.supercard.repository;

public class BaseRepository {

    protected String defaultEnv = "local";
    protected RepositoryHelper repositoryHelper = new RepositoryHelper(defaultEnv);
}
