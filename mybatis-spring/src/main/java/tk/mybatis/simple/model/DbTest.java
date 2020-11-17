package tk.mybatis.simple.model;

public class DbTest {

    public Integer id;
    public String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "DbTest{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
