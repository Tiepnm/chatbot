package vertx.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import io.netty.util.CharsetUtil;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import vertx.request.AccountResponse;

/**
 * Created by Administrator on 5/24/2017.
 */
public class Mycodec2 implements MessageCodec<AccountResponse, String> {
    @Override
    public void encodeToWire(Buffer buffer, AccountResponse accountResponse) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString;
        try {
            jsonInString = mapper.writeValueAsString(accountResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = jsonInString.getBytes(CharsetUtil.UTF_8);
        buffer.appendInt(bytes.length);
        buffer.appendBytes(bytes);
    }

    @Override
    public String decodeFromWire(int pos, Buffer buffer) {
        int length = buffer.getInt(pos);
        pos += 4;
        byte[] bytes = buffer.getBytes(pos, pos + length);
        return new String(bytes, CharsetUtil.UTF_8);
    }

    @Override
    public String transform(AccountResponse accountResponse) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonInString = mapper.writeValueAsString(accountResponse);
            return jsonInString;
        } catch (JsonProcessingException e) {
            throw new RuntimeJsonMappingException("UserDetailsStateRequestCodec, Error converting userDetailsStateRequestDTO obj into json");
        }
    }

    @Override
    public String name() {
        return AccountResponse.class.getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
