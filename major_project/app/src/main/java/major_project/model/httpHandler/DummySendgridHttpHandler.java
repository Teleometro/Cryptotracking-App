package major_project.model.httpHandler;

public class DummySendgridHttpHandler implements SendgridHttpHandler{

    public String postEmail(String key, String data) {
        return "{}";
    }
}
