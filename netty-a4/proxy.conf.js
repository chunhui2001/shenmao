const PROXY_CONFIG = [
  {
    context: [
      "/index",
      "/account",
      "/logout",
      "/login",
      "/404",
      "/members",
      "/bills",

      "/assets",
      "/libs",
      "/fonts",
      "/jquery",
      "/gallery"

    ],
    target: "http://localhost:8844",
    secure: false,
    bypass: function (req, res, proxyOptions) {

      // if (req.headers.accept.indexOf("html") !== -1) {
      //   console.log("Skipping proxy for browser request.");
      //   return "/index.html";
      // }
      //
      // req.headers["X-Custom-Header"] = "yes";

    }
  }
]

module.exports = PROXY_CONFIG;
