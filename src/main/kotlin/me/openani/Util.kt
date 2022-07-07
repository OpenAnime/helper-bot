package me.openani

import me.openani.URL
import me.openani.USER
import me.openani.PASSWORD
import java.sql.DriverManager;
import java.sql.Connection;

fun getConnection(): Connection {
    Class.forName("org.mariadb.jdbc.Driver")
    val conn: Connection = DriverManager.getConnection(URL, USER, PASSWORD)

    return conn
}

fun syncTables() {
    var conn = getConnection()

    var st = conn.createStatement()

    st.executeQuery("""
        CREATE TABLE IF NOT EXISTS suggestions(
            caseNum VARCHAR(255),
            authorId VARCHAR(255),
            content VARCHAR(4000),
            confirm BOOLEAN DEFAULT FALSE 
        );
    """.trimIndent())

    st.close()
    conn.close()
}

fun getSuggestion(id: String): Any? {
    var conn = getConnection()

    var st = conn.createStatement()

    var res = st.executeQuery("""
        SELECT * FROM suggestion WHERE caseNum = $id
    """.trimIndent())

    st.close()
    conn.close()

    if (res.first()) {
        return object {
            val caseNum = res.getString(1)
            val url = res.getString(2)
            val author = res.getString(3)
        }
    }

    return null
}

fun getCaseNum(): Int {
    val conn = getConnection()
    val st = conn.createStatement()

    val res = st.executeQuery("SELECT COUNT(*) FROM suggestions;")
    res.first()

    val caseNum = res.getInt(1)

    st.close()
    conn.close()
    res.close()

    return caseNum
}

fun createSuggestion(id: String, content: String) {
    val conn = getConnection()
    val st = conn.createStatement()

    val res = st.executeQuery("SELECT COUNT(*) FROM suggestions;")
    res.first()

    val caseNum = res.getInt(1)

    st.executeQuery("INSERT INTO suggestions(caseNum, authorId, content) VALUES('$caseNum', '$id', '$content');")

    st.close()
    conn.close()
    res.close()
}