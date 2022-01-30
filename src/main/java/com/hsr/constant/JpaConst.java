package com.hsr.constant;

public interface JpaConst {

    // meta information
    // per page これは他のコンストファイルに分離するかも
    int ROW_PER_PAGE = 10; //1ページに表示するレコードの数
    int PAGE_PER_PAGE = 10; //ページネーションに表示するページの最大数


    // database
    // table
    public static final String TABLE_USER = "users";
    /*
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_NAME = "name";
    public static final String COL_USER_GENDER = "gender";
    public static final String COL_USER_BIRTHDAY = "birthday";
    public static final String COL_USER_INTRODUCTION = "introduction";
    public static final String COL_USER_CREATED_AT = "created_at";
    */


    public static final String TABLE_BOOKSHELF = "bookshelfs";
    /*
    public static final String COL_BOOKSHELF_ID = "id";
    public static final String COL_BOOKSHELF_USER_ID = "user_id";
    public static final String COL_BOOKSHELF_NAME = "name";
    public static final String COL_BOOKSHELF_DESCRIPTION = "description";
    public static final String COL_BOOKSHELF_CREATED_AT = "created_at";
    public static final String COL_BOOKSHELF_UPDATED_AT = "updated_at";
    */


    // entity
    public static final String ENTITY_USER = "User";
    public static final String ENTITY_BOOKSHELF = "Bookshelf";

    // flag
    public static final int DELETE_FLAG_FALSE = 0;
    public static final int DELETE_FLAG_TRUE =1;
    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;


    // jpql
    // parameter
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PASS = "password";

    // query
    public static final String BOOKSHELF_GETPAGES = "select bs from " + ENTITY_BOOKSHELF + " as bs order by bs.createdAt desc";

}
