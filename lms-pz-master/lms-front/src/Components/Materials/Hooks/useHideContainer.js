import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
export default function useHideContainer(init) {
  const [load, setLoad] = useState(init);

  const location = useLocation();

  useEffect(() => {
    let hide = true;
    const currentPath = location.pathname;
    if (currentPath === "/materials") {
      hide = false;
    }
    setLoad(!hide);
  }, [location.pathname]);

  return load;
}
