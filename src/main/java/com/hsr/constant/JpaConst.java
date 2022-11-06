package com.hsr.constant;

public interface JpaConst {

    // meta information
    // per page これは他のコンストファイルに分離するかも
    int ROW_PER_PAGE = 10; //1ページに表示するレコードの数
    int PAGE_PER_PAGE = 10; //ページネーションに表示するページの最大数


    // database
    // table
    public static final String TABLE_USER = "users";
    public static final String TABLE_BOOKSHELF = "bookshelfs";
    public static final String TABLE_BOOK = "books";

    // entity
    public static final String ENTITY_USER = "User";
    public static final String ENTITY_BOOKSHELF = "Bookshelf";
    public static final String ENTITY_BOOK = "Book";

    // flag
    public static final int DELETE_FLAG_FALSE = 0;
    public static final int DELETE_FLAG_TRUE =1;
    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;


    // jpql
    // parameter
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PASS = "password";
    public static final String PARAM_BOOKSHELF = "bookshelf";
    public static final String PARAM_USER = "user";

    // query
    public static final String USER_GET_BY_EMAIL = "select u from " + ENTITY_USER + " as u" +
                                                      " where u.email = :" + PARAM_EMAIL;
    public static final String BOOKSHELF_GETPAGES = "select bs from " + ENTITY_BOOKSHELF + " as bs" +
                                                        " where bs.deleteFlag = " + DELETE_FLAG_FALSE +
                                                        " order by bs.createdAt desc";
    public static final String BOOKSHELF_GETPAGES_SPECIFIED_USER = "select bs from " + ENTITY_BOOKSHELF + " as bs" +
                                                        " where bs.deleteFlag = " + DELETE_FLAG_FALSE +
                                                        " and bs.user = :" + PARAM_USER +
                                                        " order by bs.createdAt desc";
    public static final String BOOK_GETPAGES = "select b from " + ENTITY_BOOK + " as b" +
                                                        " order by b.createdAt desc";
    public static final String BOOK_GETPAGES_IN_BOOKSHELF = "select b from " + ENTITY_BOOK + " as b" +
                                                        " where b.bookshelf = :" + PARAM_BOOKSHELF +
                                                        " and b.deleteFlag = " + DELETE_FLAG_FALSE +
                                                        " order by b.createdAt desc";
}
