<h1>Assignment Scalable Web</h1>

WAES • Willemstraat 1e • 5611 HA • Eindhoven• +31(0)40 303 21 50 • www.wearewaes.com

<h2>Goal</h2>
The goal of this assignment is to show your coding skills and what you value in software
engineering. We value new ideas so next to the original requirement feel free to
improve/add/extend.
We evaluate the assignment depending on your role (Developer/Tester) and your level of
seniority.
<h2>The assignment</h2>
• Provide 2 http endpoints that accepts JSON base64 encoded binary data on both
endpoints
o <host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right
• The provided data needs to be diff-ed and the results shall be available on a third end
point
o <host>/v1/diff/<ID>
• The results shall provide the following info in JSON format
o If equal return that
o If not of equal size just return that
o If of same size provide insight in where the diffs are, actual diffs are not needed.
§ So mainly offsets + length in the data

• Make assumptions in the implementation explicit, choices are good but need to be
communicated
<h2>Must haves</h2>
• Solution written in Java
• Internal logic shall be under unit test
• Functionality shall be under integration test
• Documentation in code
• Clear and to the point readme on usage
<h2>Nice to haves</h2>
• Suggestions for improvement


<h2>Solution Details:</h2>
Diff Service is written on Java and in the Demo version uses transient storage (without saving Data to DB).
Future versions of the application use Postgres.
For Internal Service communication is chosen Event Bus solution based on Application-level events.

<h2>Port</h2>
Application run on port: 8090.
localhost:8090/

<h3>HTTP Endpoints to use</h3>
</ul>
<li>GET /v1/diff/{id}/ - GET Comparision Result</li>
<li>POST /v1/diff/{id}/left - Create model for the Left part of comparision data</li>
<li>POST /v1/diff/{id}/right - Create model for the Right part of comparision data</li>