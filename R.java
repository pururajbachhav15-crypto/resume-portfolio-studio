import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class R {

    // ---------------- HTML SECURITY ----------------

    static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    static String card(String title, String content) {
        return "<div class='card'><h2>" + title + "</h2>" + content + "</div>";
    }

    static String input(String label, String name, String type) {
        return "<label>" + label + "</label>" +
               "<input type='" + type + "' name='" + name + "'>";
    }

    static String area(String label, String name) {
        return "<label>" + label + "</label>" +
               "<textarea name='" + name + "' rows='4'></textarea>";
    }

    // ---------------- CSS (DISTINCT STYLES FOR RESUME & PORTFOLIO) ----------------

    static String css() {
        return "<style>" +
        "*{box-sizing:border-box}" +
        "body{margin:0;background:#f8fafc;font-family:Arial,sans-serif;color:#1e293b}" +
        ".nav{background:#0f172a;color:white;padding:18px 25px;font-size:20px;font-weight:bold;letter-spacing:0.5px}" +
        ".container{max-width:900px;margin:30px auto;padding:15px}" +
        ".card{background:white;padding:25px;margin:18px 0;border-radius:12px;box-shadow:0 1px 3px rgba(0,0,0,0.1)}" +
        "h1{font-size:28px}h2{color:#0f172a}" +
        "label{display:block;margin-top:15px;font-weight:bold;color:#475569}" +
        "input,textarea{width:100%;padding:12px;margin-top:7px;border:1px solid #cbd5e1;border-radius:6px;font-size:15px}" +
        "textarea{resize:vertical}" +
        ".btn{display:inline-block;background:#2563eb;color:white;padding:12px 20px;border:0;border-radius:6px;text-decoration:none;font-size:14px;font-weight:bold;margin-top:15px;cursor:pointer}" +
        ".btn2{background:#0d9488}" +
        ".hero{text-align:center;padding:70px 20px;background:linear-gradient(135deg, #0f172a, #1e293b);color:white}" +
        ".hero h1{font-size:38px;color:white;margin-bottom:10px}" +
        ".hero p{font-size:18px;color:#94a3b8;line-height:1.5}" +
        
        /* Resume Specific Styles */
        ".resume-doc{background:white;padding:45px;border-radius:8px;box-shadow:0 4px 6px -1px rgba(0,0,0,0.1);border-top:6px solid #2563eb}" +
        ".resume-header{border-bottom:2px solid #e2e8f0;padding-bottom:20px;margin-bottom:25px}" +
        ".resume-header h1{margin:0 0 5px 0;color:#0f172a;font-size:32px}" +
        ".resume-header .role{font-size:18px;color:#2563eb;font-weight:bold;margin-bottom:10px}" +
        ".resume-header .contact{font-size:14px;color:#64748b}" +
        ".resume-section{margin-bottom:25px}" +
        ".resume-section h3{font-size:16px;text-transform:uppercase;letter-spacing:1px;color:#475569;border-bottom:1px solid #e2e8f0;padding-bottom:5px;margin-bottom:12px}" +

        /* Portfolio Specific Styles */
        ".portfolio-doc{background:white;padding:45px;border-radius:12px;box-shadow:0 10px 15px -3px rgba(0,0,0,0.1);border-top:6px solid #0d9488}" +
        ".portfolio-top{display:flex;align-items:center;gap:25px;border-bottom:2px solid #f1f5f9;padding-bottom:25px;margin-bottom:25px}" +
        ".portfolio-top img{width:120px;height:120px;border-radius:50%;object-fit:cover;border:3px solid #0d9488}" +
        ".portfolio-grid{display:grid;grid-template-columns:1fr 1fr;gap:20px;margin-top:20px}" +
        ".project-box{background:#f8fafc;padding:18px;border-radius:8px;border-left:4px solid #0d9488}" +

        ".muted{color:#64748b}" +
        "@media(max-width:600px){.portfolio-top{flex-direction:column;text-align:center}.portfolio-grid{grid-template-columns:1fr}.container{margin:10px auto}}" +
        "@media print{.nav,.no-print{display:none!important}body{background:white}.resume-doc,.portfolio-doc{box-shadow:none;border:0;padding:0}}" +
        "</style>";
    }

    // ---------------- WELCOME PAGE ----------------

    static String welcome() {
        return "<div class='hero'>" +
               "<h1>Welcome to Resume Studio</h1>" +
               "<p>Craft clean corporate resumes or gorgeous developer portfolios in seconds.</p>" +
               "<a class='btn' href='/home'>GET STARTED</a>" +
               "</div>";
    }

    // ---------------- HOME PAGE ----------------

    static String home() {
        return "<div class='container'>" +
               "<h1>Resume Studio</h1>" +
               "<p class='muted'>Select a studio layout below.</p>" +

               card("Professional Resume Builder",
               "<p>Structured, ATS-friendly corporate resume format optimized for job applications.</p>" +
               "<a class='btn' href='/resume'>Build Resume</a>") +

               card("Portfolio & Showcase Builder",
               "<p>Visual, modern portfolio format featuring a profile photo, bio, and project highlights.</p>" +
               "<a class='btn btn2' href='/portfolio'>Build Portfolio</a>") +

               "</div>";
    }

    // ---------------- RESUME FORM ----------------

    static String resumeForm() {
        return "<div class='container'>" +
               "<h1>Create Your Resume</h1>" +
               "<form method='POST' action='/resume-result'>" +

               card("Personal Details",
               input("Full Name", "name", "text") +
               input("Job Role / Designation", "role", "text") +
               input("Phone Number", "phone", "tel") +
               input("Email Address", "email", "email") +
               input("Location", "location", "text")) +

               card("Professional Summary",
               area("About Yourself", "summary")) +

               card("Work Experience",
               area("Experience / Responsibilities", "experience")) +

               card("Education",
               area("Education Details", "education")) +

               card("Skills",
               area("Skills (separate with commas)", "skills")) +

               card("Achievements",
               area("Achievements", "achievements")) +

               "<button class='btn' type='submit'>Generate Resume</button>" +
               "</form></div>";
    }

    // ---------------- PORTFOLIO FORM ----------------

    static String portfolioForm() {
        return "<div class='container'>" +
               "<h1>Create Your Portfolio</h1>" +
               "<form method='POST' action='/portfolio-result'>" +

               card("Personal Details",
               input("Full Name", "name", "text") +
               input("Professional Role", "role", "text") +
               input("Photo URL (https://...)", "photo", "url") +
               input("Email", "email", "email") +
               input("Phone", "phone", "tel") +
               input("Location", "location", "text")) +

               card("About Me",
               area("Write About Yourself", "about")) +

               card("Skills",
               area("Your Skills (comma separated)", "skills")) +

               card("Projects",
               area("Key Projects & Description", "projects")) +

               card("Experience",
               area("Work Experience", "experience")) +

               card("Education",
               area("Education Details", "education")) +

               card("Achievements",
               area("Achievements", "achievements")) +

               card("Social Links",
               input("LinkedIn URL", "linkedin", "url") +
               input("Personal Website", "website", "url")) +

               "<button class='btn btn2' type='submit'>Generate Portfolio</button>" +
               "</form></div>";
    }

    // ---------------- SKILLS CHIPS ----------------

    static String chips(String skills) {
        if (skills == null || skills.trim().isEmpty()) return "";
        StringBuilder b = new StringBuilder();
        String[] items = skills.split(",");
        for (String item : items) {
            if (!item.trim().isEmpty()) {
                b.append("<span style='display:inline-block;background:#e2e8f0;color:#334155;padding:6px 12px;margin:4px;border-radius:16px;font-size:13px;font-weight:600'>");
                b.append(esc(item.trim()));
                b.append("</span>");
            }
        }
        return b.toString();
    }

    // ---------------- FORM DATA PARSING ----------------

    static Map<String, String> parse(String body) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        String[] pairs = body.split("&");
        for (String pair : pairs) {
            String[] parts = pair.split("=", 2);
            String key = URLDecoder.decode(parts[0], "UTF-8");
            String value = "";
            if (parts.length > 1) {
                value = URLDecoder.decode(parts[1], "UTF-8");
            }
            map.put(key, value);
        }
        return map;
    }

    // ---------------- FORMAT 1: RESUME RESULT ----------------

    static String resumeResult(Map<String, String> m) {
        String name = esc(m.get("name"));
        String role = esc(m.get("role"));
        String email = esc(m.get("email"));
        String phone = esc(m.get("phone"));
        String location = esc(m.get("location"));

        StringBuilder b = new StringBuilder();
        b.append("<div class='container'>");
        b.append("<div class='no-print' style='margin-bottom: 15px;'>");
        b.append("<button class='btn' onclick='window.print()'>Print / Save PDF</button> ");
        b.append("<button class='btn btn2' onclick='history.back()'>Edit Resume</button>");
        b.append("</div>");

        b.append("<div class='resume-doc'>");
        
        // Header
        b.append("<div class='resume-header'>");
        b.append("<h1>").append(name).append("</h1>");
        b.append("<div class='role'>").append(role).append("</div>");
        b.append("<div class='contact'>").append(email);
        if (!email.isEmpty() && !phone.isEmpty()) b.append(" &bull; ");
        b.append(phone);
        if (!location.isEmpty()) b.append(" &bull; ").append(location);
        b.append("</div></div>");

        // Summary
        b.append("<div class='resume-section'><h3>Professional Summary</h3>");
        b.append("<p>").append(esc(m.get("summary"))).append("</p></div>");

        // Skills
        b.append("<div class='resume-section'><h3>Core Skills</h3>");
        b.append(chips(m.get("skills"))).append("</div>");

        // Experience
        b.append("<div class='resume-section'><h3>Work Experience</h3>");
        b.append("<p>").append(esc(m.get("experience"))).append("</p></div>");

        // Education
        b.append("<div class='resume-section'><h3>Education</h3>");
        b.append("<p>").append(esc(m.get("education"))).append("</p></div>");

        // Achievements
        b.append("<div class='resume-section'><h3>Achievements</h3>");
        b.append("<p>").append(esc(m.get("achievements"))).append("</p></div>");

        b.append("</div></div>");
        return b.toString();
    }

    // ---------------- FORMAT 2: PORTFOLIO RESULT ----------------

    static String portfolioResult(Map<String, String> m) {
        String name = esc(m.get("name"));
        String role = esc(m.get("role"));
        String email = esc(m.get("email"));
        String phone = esc(m.get("phone"));
        String location = esc(m.get("location"));
        String photo = m.get("photo");

        StringBuilder b = new StringBuilder();
        b.append("<div class='container'>");
        b.append("<div class='no-print' style='margin-bottom: 15px;'>");
        b.append("<button class='btn btn2' onclick='window.print()'>Print / Save Portfolio PDF</button> ");
        b.append("<button class='btn' onclick='history.back()'>Edit Portfolio</button>");
        b.append("</div>");

        b.append("<div class='portfolio-doc'>");

        // Hero Profile Top
        b.append("<div class='portfolio-top'>");
        if (photo != null && (photo.startsWith("https://") || photo.startsWith("http://"))) {
            b.append("<img src='").append(esc(photo)).append("' alt='Profile Photo'>");
        }
        b.append("<div>");
        b.append("<h1 style='margin:0 0 5px 0; color:#0f172a;'>").append(name).append("</h1>");
        b.append("<p style='margin:0 0 10px 0; font-size:16px; color:#0d9488; font-weight:bold;'>").append(role).append("</p>");
        b.append("<p style='margin:0; font-size:14px; color:#64748b;'>").append(email);
        if (!email.isEmpty() && !phone.isEmpty()) b.append(" | ");
        b.append(phone);
        if (!location.isEmpty()) b.append(" | ").append(location);
        b.append("</p></div></div>");

        // About Me
        b.append("<div style='margin-bottom:25px;'>");
        b.append("<h2 style='color:#0d9488;'>About Me</h2>");
        b.append("<p style='line-height:1.6; color:#334155;'>").append(esc(m.get("about"))).append("</p>");
        b.append("</div>");

        // Skills
        b.append("<div style='margin-bottom:25px;'>");
        b.append("<h2 style='color:#0d9488;'>Tech Stack & Skills</h2>");
        b.append(chips(m.get("skills")));
        b.append("</div>");

        // Projects Showcase Grid
        b.append("<div style='margin-bottom:25px;'>");
        b.append("<h2 style='color:#0d9488;'>Featured Projects</h2>");
        b.append("<div class='project-box'>");
        b.append("<p style='margin:0; line-height:1.6;'>").append(esc(m.get("projects"))).append("</p>");
        b.append("</div></div>");

        // Experience & Education Grid
        b.append("<div class='portfolio-grid'>");
        b.append("<div><h3 style='color:#0f172a;'>Experience</h3><p style='color:#334155;'>").append(esc(m.get("experience"))).append("</p></div>");
        b.append("<div><h3 style='color:#0f172a;'>Education</h3><p style='color:#334155;'>").append(esc(m.get("education"))).append("</p></div>");
        b.append("</div>");

        // Achievements & Links
        b.append("<div style='margin-top:25px; border-top:1px solid #f1f5f9; padding-top:20px;'>");
        b.append("<h3 style='color:#0f172a;'>Achievements</h3>");
        b.append("<p style='color:#334155;'>").append(esc(m.get("achievements"))).append("</p>");

        String linkedin = m.get("linkedin");
        String website = m.get("website");
        if ((linkedin != null && !linkedin.trim().isEmpty()) || (website != null && !website.trim().isEmpty())) {
            b.append("<div style='margin-top:15px;'><strong>Links:</strong> ");
            if (linkedin != null && !linkedin.trim().isEmpty()) {
                b.append("<a href='").append(esc(linkedin)).append("' target='_blank' style='color:#0d9488; margin-right:15px;'>LinkedIn</a>");
            }
            if (website != null && !website.trim().isEmpty()) {
                b.append("<a href='").append(esc(website)).append("' target='_blank' style='color:#0d9488;'>Website</a>");
            }
            b.append("</div>");
        }
        b.append("</div>");

        b.append("</div></div>");
        return b.toString();
    }

    // ---------------- READ HTTP HEADERS ----------------

    static String readHeaders(InputStream in) throws Exception {
        StringBuilder b = new StringBuilder();
        int ch;
        int state = 0;
        while ((ch = in.read()) != -1) {
            b.append((char) ch);
            if (state == 0 && ch == '\r') state = 1;
            else if (state == 1 && ch == '\n') state = 2;
            else if (state == 2 && ch == '\r') state = 3;
            else if (state == 3 && ch == '\n') break;
            else state = 0;
            if (b.length() > 10000) break;
        }
        return b.toString();
    }

    // ---------------- SEND RESPONSE ----------------

    static void send(Socket s, String html) throws Exception {
        byte[] data = html.getBytes(StandardCharsets.UTF_8);
        OutputStream out = s.getOutputStream();
        String headers =
            "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html; charset=UTF-8\r\n" +
            "Content-Length: " + data.length + "\r\n" +
            "Connection: close\r\n" +
            "\r\n";
        out.write(headers.getBytes(StandardCharsets.UTF_8));
        out.write(data);
        out.flush();
    }

    // ---------------- HANDLE REQUEST ----------------

    static void handle(Socket s) {
        try {
            s.setSoTimeout(5000);
            InputStream in = s.getInputStream();
            String headers = readHeaders(in);
            if (headers.isEmpty()) return;

            String[] lines = headers.split("\r\n");
            String[] req = lines[0].split(" ");
            if (req.length < 2) return;

            String method = req[0];
            String path = req[1];
            if (path.contains("?")) {
                path = path.substring(0, path.indexOf("?"));
            }

            String html = "";

            if (method.equals("GET")) {
                if (path.equals("/")) html = welcome();
                else if (path.equals("/home")) html = home();
                else if (path.equals("/resume")) html = resumeForm();
                else if (path.equals("/portfolio")) html = portfolioForm();
                else html = "<h1>Page not found</h1>";

            } else if (method.equals("POST")) {
                int length = 0;
                for (String line : lines) {
                    if (line.toLowerCase().startsWith("content-length:")) {
                        length = Integer.parseInt(line.split(":")[1].trim());
                    }
                }

                if (length < 0 || length > 50000) {
                    send(s, "<h1>Invalid request</h1>");
                    return;
                }

                byte[] body = new byte[length];
                int pos = 0;
                while (pos < length) {
                    int n = in.read(body, pos, length - pos);
                    if (n < 0) break;
                    pos += n;
                }

                if (pos != length) return;

                Map<String, String> m = parse(new String(body, StandardCharsets.UTF_8));

                if (path.equals("/resume-result")) {
                    html = resumeResult(m);
                } else if (path.equals("/portfolio-result")) {
                    html = portfolioResult(m);
                } else {
                    html = "<h1>Page not found</h1>";
                }
            } else {
                send(s, "<h1>Method not allowed</h1>");
                return;
            }

            send(s, css() + "<div class='nav'>RESUME & PORTFOLIO STUDIO</div>" + html);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try { s.close(); } catch (Exception e) {}
        }
    }

    // ---------------- MAIN SERVER ----------------

    public static void main(String[] args) {
        int port = 8080;
        ExecutorService pool = Executors.newFixedThreadPool(20);

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("================================");
            System.out.println("   STUDIO SERVER STARTED (8080) ");
            System.out.println("================================");
            System.out.println("Open browser: http://127.0.0.1:8080/");
            System.out.println("================================");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nShutting down server thread pool...");
                pool.shutdown();
            }));

            while (true) {
                Socket client = server.accept();
                pool.submit(() -> handle(client));
            }
        } catch (Exception e) {
            System.out.println("Server Error: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }
}
