import { useEffect, useState, useReducer } from "react";

function apiReducer(state, action) {
  switch (action.type) {
    case "fetching":
      return { ...state, isLoading: true };
    case "success":
      return {
        data: action.payload,
        isLoading: false,
        error: null,
        hasData: true,
      };
    case "error":
      return {
        data: null,
        isLoading: false,
        error: action.payload,
        hasData: false,
      };
    default:
      return state;
  }
}

async function doFetch(path) {
  const token = JSON.parse(localStorage.getItem("token")); //Wazne - musisz poprosic bartka o wygenerowanie tokenu bo ja korzystam z tokenu uzytkownika aktualnie zalogowanego, i wtedy zamiast tej linijcki JSON.parse... wrzucic po prostu token, czyli cos takiego const token = 'abcd';

  const headers = {
    Authorization: `Bearer ${token}`,
  };

  const fetchData = {
    method: "GET",
    headers: headers,
  };
  const res = await fetch(path, fetchData);
  if (res.ok) {
    return res.json();
  }
  throw await res.json();
}

export default function useApi(path) {
  const [response, dispatch] = useReducer(apiReducer, {
    data: null,
    isLoading: false,
    error: null,
    hasData: false,
  });
  const [isMounted, setIsMounted] = useState(true);

  useEffect(
    function () {
      setIsMounted(true);
      const getData = async function () {
        dispatch({ type: "fetching" });
        try {
          const data = await doFetch(path);
          if (isMounted) {
            dispatch({ type: "success", payload: data });
          }
        } catch (error) {
          if (isMounted) dispatch({ type: "error", payload: "error Data" });
        }
      };
      getData();
      return () => {
        setIsMounted(false);
      };
    },
    [path, isMounted]
  );

  return response;
}
