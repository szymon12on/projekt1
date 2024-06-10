import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

export default function useNameMaterials(init) {
  const [nameMaterials, setNameMaterials] = useState(init);
  const location = useLocation();

  useEffect(
    function () {
      const currentPath = location.pathname;
      if (currentPath === "/materials") {
        setNameMaterials("Materials");
      }
    },
    [location.pathname]
  );
  return [nameMaterials, setNameMaterials];
}
