skip :: Int ->Int -> Bool
skip 0 n = even n
skip 1 n = odd n
skip _ n = n `mod` 3 == 0

pass ::Int -> [a] -> [a]
pass k = go 1
  where
    go _ [] = []
    go i (x:xs)
      | skip k i = go (i+1) xs
      | otherwise = x : go (i+1)xs

solve :: Int ->Int
solve n = iter 0 [1..n]
  where
    iter _ [x] = x
    iter k xs = iter ((k+1) `mod` 3) (pass k xs)

main :: IO ()
main =do
  n <- readLn
  print (solve n)