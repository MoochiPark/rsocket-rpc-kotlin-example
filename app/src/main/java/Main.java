import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jfree.data.xy.XYSeries;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Main {


  private static void runProfiler(String title, Profiler.Timeable timeable, int startN, int endMillis) throws IOException {
    Profiler profiler = new Profiler(title, timeable);
    XYSeries series = profiler.timingLoop(startN, endMillis);
    profiler.plotResults(series);
  }

  public static void main(String[] args) throws IOException {
    Profiler.Timeable rsocket = new Profiler.Timeable() {

      CloseableHttpClient client;

      HttpPost httpPost;

      @Override
      public void setup(final int n) throws UnsupportedEncodingException {
        client = HttpClients.createDefault();
        httpPost = new HttpPost("http://localhost:8080/payment");
        httpPost.addHeader("content-type", "application/json");
        StringEntity body = new StringEntity("{ \"userId\": 1, \"productId\": 1 }");
        httpPost.setEntity(body);
      }

      @Override
      public void timeMe(final int n) throws IOException {
        final CloseableHttpResponse execute = client.execute(httpPost);
      }
    };

    final int endMillis = 30_000;
    runProfiler("RSocket RPC", rsocket, 1, endMillis);
  }

}
