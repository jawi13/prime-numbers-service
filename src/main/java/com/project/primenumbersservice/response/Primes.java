package com.project.primenumbersservice.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.StringJoiner;

@Setter
@Getter
@NoArgsConstructor
@Component
public class Primes {

    public Integer initial;
    public List<Integer> primes;

    public String asJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Initial", initial);
        jsonObject.put("Primes", primes);
        return jsonObject.toJSONString();
    }

    public String asXmlString() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        String indent = "  ";
        stringJoiner.add("<Primes>");
        stringJoiner.add(indent + "<initial>" + initial.toString() + "</initial>");
        stringJoiner.add(indent + "<primes>");
        for (Integer prime : primes) {
            stringJoiner.add(indent + indent + "<primes>" + prime.toString() + "</primes>");
        }
        stringJoiner.add(indent + "</primes>");
        stringJoiner.add("</Primes>");
        return stringJoiner.toString();
    }
}
