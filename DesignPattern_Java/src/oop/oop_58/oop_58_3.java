
// 模板模式作用二：扩展

// 1.Java Servlet



public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      this.doPost(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      resp.getWriter().write("Hello World.");
    }
  }


  
// <servlet>
// <servlet-name>HelloServlet</servlet-name>
// <servlet-class>com.xzg.cd.HelloServlet</servlet-class>
// </servlet>

// <servlet-mapping>
// <servlet-name>HelloServlet</servlet-name>
// <url-pattern>/hello</url-pattern>
// </servlet-mapping>


public void service(ServletRequest req, ServletResponse res)
    throws ServletException, IOException
{
    HttpServletRequest  request;
    HttpServletResponse response;
    if (!(req instanceof HttpServletRequest &&
            res instanceof HttpServletResponse)) {
        throw new ServletException("non-HTTP request or response");
    }
    request = (HttpServletRequest) req;
    response = (HttpServletResponse) res;
    service(request, response);
}

protected void service(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
{
    String method = req.getMethod();
    if (method.equals(METHOD_GET)) {
        long lastModified = getLastModified(req);
        if (lastModified == -1) {
            // servlet doesn't support if-modified-since, no reason
            // to go through further expensive logic
            doGet(req, resp);
        } else {
            long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
            if (ifModifiedSince < lastModified) {
                // If the servlet mod time is later, call doGet()
                // Round down to the nearest second for a proper compare
                // A ifModifiedSince of -1 will always be less
                maybeSetLastModified(resp, lastModified);
                doGet(req, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            }
        }
    } else if (method.equals(METHOD_HEAD)) {
        long lastModified = getLastModified(req);
        maybeSetLastModified(resp, lastModified);
        doHead(req, resp);
    } else if (method.equals(METHOD_POST)) {
        doPost(req, resp);
    } else if (method.equals(METHOD_PUT)) {
        doPut(req, resp);
    } else if (method.equals(METHOD_DELETE)) {
        doDelete(req, resp);
    } else if (method.equals(METHOD_OPTIONS)) {
        doOptions(req,resp);
    } else if (method.equals(METHOD_TRACE)) {
        doTrace(req,resp);
    } else {
        String errMsg = lStrings.getString("http.method_not_implemented");
        Object[] errArgs = new Object[1];
        errArgs[0] = method;
        errMsg = MessageFormat.format(errMsg, errArgs);
        resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
    }
}

// 2.JUnit TestCase


public abstract class TestCase extends Assert implements Test {
    public void runBare() throws Throwable {
      Throwable exception = null;
      setUp();
      try {
        runTest();
      } catch (Throwable running) {
        exception = running;
      } finally {
        try {
          tearDown();
        } catch (Throwable tearingDown) {
          if (exception == null) exception = tearingDown;
        }
      }
      if (exception != null) throw exception;
    }
    
    /**
    * Sets up the fixture, for example, open a network connection.
    * This method is called before a test is executed.
    */
    protected void setUp() throws Exception {
    }
  
    /**
    * Tears down the fixture, for example, close a network connection.
    * This method is called after a test is executed.
    */
    protected void tearDown() throws Exception {
    }
  }