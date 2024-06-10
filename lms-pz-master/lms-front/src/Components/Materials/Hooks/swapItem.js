export default function swapItem(arr) {
  let a = arr[1];
  let b = arr[2];
  arr[1] = b;
  arr[2] = a;
  return arr;
}
