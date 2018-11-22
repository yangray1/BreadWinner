package com.example.crystalyip.csc301;

import com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void postTestJSOn() throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "tasbear");
        jsonObject.put("nose-size", 100);
        String poyo = HTTPRequests.postHTTPJson("http://ptsv2.com/tasbir/l1z6f-1542848485/post", jsonObject);
        assertEquals(poyo, "poyo");
    }
}