<%@ page pageEncoding="UTF-8" %>
<html>
  <head>
      <title>Une première JSP</title>
  </head>
  <body>
      <% for (int i =  0 ; i < 2 ; i++) { %>
          <p>Bonjour le monde ! <%= i %></p>
      <% } %>
  </body>
</html>
