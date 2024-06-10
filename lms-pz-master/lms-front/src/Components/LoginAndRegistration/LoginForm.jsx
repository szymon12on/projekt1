import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import ErrorDisplay from "./ErrorDisplay";
import UserContext from "../Context/UserContext";

const headers = {
  "Content-Type": "application/json",
};

function LoginForm() {
  const [data, setData] = useState({
    login: "",
    haslo: "",
  });
  const [err, setErr] = useState({
    error: false,
    message: "",
  });
  const navigate = useNavigate();
  const { setUserInfo } = useContext(UserContext);

  function handleError(message) {
    setErr({ error: true, message: message });
  }

  function handleclearState() {
    setData({ login: "", haslo: "" });
  }

  function handleInput(e) {
    setData({ ...data, [e.target.name]: e.target.value });
  }

  const handleSubmit = async function (e) {
    e.preventDefault();

    try {
      const res = await fetch("/api/v1/auth/login", {
        method: "POST",
        headers: headers,
        body: JSON.stringify(data),
      });

      if (!res.ok) {
        throw new Error("Blad w logowaniu");
      }
      const answer = await res.json();
      setUserInfo({ id: answer.id, login: answer.login, token: answer.token });
      localStorage.setItem("token", JSON.stringify(answer.token));
      navigate("/home");
    } catch (err) {
      handleclearState();
      handleError(err.message);
    }
  };

  return (
    <div className="flex flex-col">
      <h1 className="text-3xl ml-10 mt-10">Zaloguj się do konta</h1>
      <p className="text-gray opacity-55 ml-10 mt-1">Zacznij z nami...</p>
      <form className="flex flex-col gap-y-10 px-10 mt-10">
        <label className="flex flex-col text-blue ">
          Login:
          <input
            type="text"
            className="py-2 px-3 mt-1 outline-none text-lg bg-white border-[1px] border-gray border-opacity-20 rounded-lg text-black"
            placeholder="Username..."
            name="login"
            onChange={handleInput}
            value={data.login}
          />
        </label>
        <label className="flex flex-col  text-blue ">
          Hasło:
          <input
            type="password"
            className="py-2 px-3 mt-1 outline-none text-lg bg-white border-[1px] border-gray border-opacity-20 rounded-lg text-black "
            name="haslo"
            placeholder="Password..."
            onChange={handleInput}
            value={data.haslo}
          />
        </label>
        <div className="flex justify-between">
          <label className="flex gap-x-3 text-gray opacity-80">
            <input type="checkbox" />
            Zapamiętaj mnie
          </label>
          <p className="text-blue cursor-pointer text-[13px]">
            Zapomniałeś hasła?
          </p>
        </div>
        <div>
          <button
            className="bg-blue text-white rounded-lg py-3 mt-5 w-[100%]"
            onClick={(e) => handleSubmit(e)}
          >
            Zaloguj
          </button>
          <div className="flex mt-10 items-center justify-between gap-x-3">
            <p className="text-gray opacity-30 cursor-pointer">
              Nie masz konta?
            </p>
            <button className="bg-white border border-gray border-opacity-30 rounded-lg py-3 mt-1 px-10">
              Zarejestruj
            </button>
          </div>
        </div>
      </form>
      {err.error && <ErrorDisplay message={err.message} />}
    </div>
  );
}

export default LoginForm;
