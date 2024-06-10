export default function encodeToBase64(str) {
  const encoder = new TextEncoder("utf-8");
  const bytes = encoder.encode(str);
  const base64 = btoa(String.fromCharCode(...bytes));
  return base64;
}
