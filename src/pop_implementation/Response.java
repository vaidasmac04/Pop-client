package pop_implementation;

import java.util.ArrayList;
import java.util.List;

public class Response implements IResponse{
    private List<String> response;

    public Response(){
        response = new ArrayList<>();
    }

    @Override
    public List<String> getResponse() {
        return response;
    }

    public void appendLineToResponse(String responseToAdd){
        response.add(responseToAdd);
    }

    public void appendListToResponse(List<String> responseToAdd){
        response.addAll(responseToAdd);
    }

    @Override
    public boolean isValid() {
        if(response.get(0).startsWith("+OK")){
            return true;
        }

        return false;
    }
}
