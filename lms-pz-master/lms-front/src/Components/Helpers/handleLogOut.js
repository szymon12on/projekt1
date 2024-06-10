export default function handleLogOut() {
  localStorage.removeItem("token");
  window.location.href = "/login";
}
