// Sample products data - Đồ Dùng Học Tập
const allProducts = [
    { id: 1, name: 'Bút Bi Thiên Long TL-027', brand: 'Thiên Long', category: 'van-phong-pham', price: 3500, oldPrice: 5000, image: 'data:image/webp;base64,UklGRv4YAABXRUJQVlA4IPIYAADQWgCdASq1ALUAPlUmj0WjoiEUq81MOAVEsYHYDsv5G7d5f8Kwp9wPMX6J/2/3O/Oz/c+tn/A7tTzPftj6pn/G9c/oAftP1w3oP+XB7S37n/tHmEu0XGp7u0DMqfV9qF9q/7f+4+17+T79/jzqEeN969tt6Aver/h/3jx2f+L0K+vP/b9wD+df0n/i/2j2h/3Hg3/c/9z7Af8y/sv/R/x/5ifJ//4f6f0H/Wv/v/13wJfsJ1v/SP/cg4c7TsVKmqf/Vf57CWM6V/Iw6B4Gj3oYQqz0xOR950Nv79GSE8eFMopJWOICVWoKfcEht2L6J1FEjU/4qqQL7ycUOX73Oa3eV607VxrSTgEb0gyPVPP6Xapxier8dB9dRB/qR2DW6pA9exkukhdXq1G79JvpBuh8v0T4gWXFkhdusLIGiWb7LHVPYLY2Z8Ee+l6m5noAzdZ+F0IuwjF5IZsNpHZNW+aD3uFc4MUe6VlkLPjg9V+B/xD7hlzIyYeeTlYW/pTQE8RNA5qvGo+EH+x1V9xs8y4UGUXZ8EqEvHBM9eKJoPmEZWe1WS9pebx6E92D4vk3ReN70pmxAbi7KjUhWfDJugJU/tvN2pAlDVTqglNyp5KcNu91/mU9ppI/binXw/YKTgI8lc9wcxMpFfLCYBIl0FfdNCt9Gg9OnwoH4lgHSWWYnKGnuJADOe8EMoiF1QRaTCQzLRJQvsuxBNqCkD8m9aGiCz+LTjUNcydLaS3LjOlwxEUJJa8bkVrEnJRbTBpIh3ltTyG4JZQzjdr39FdG4XUwUUjGAJ29dl6WQVz0MYP2SsowDznBCcMjue4LMyEsX0NqnoTkpwBXBJ4JHI3yrQbX4oOmGzy0QgG9VP7cpi2Nk0Awfl0ebS6WUdo/ZKqHkApvteRyhxUZcKR4BXNkTe4sXfIBvSugfdnuZlgxdMvW50KIZ6PrpFraoT5p4FtPV+g7yszxGOSTIdINJuJMhYAA/vs7Xljw2raki4e52mvvTwc2WaFCZPvzloBJjIRGAlREf/vX7bo32+vGIKMQUbNbCx88oXUMjdAqeetX5mU/gw+1Rwnn9P1b9c3dBdOeFQPx3HuIpnSeuxfCPZ6FRv9pGRun7P8IDSJbNxToR+zbgBAEdKilImAl9vx9oWgEsAPF5iiaiXry9HLPOpMOATA8yqvv+AJhF5CQBU8CpzcK46rVL/vOk9hGnoJWn3SbymRZQlZoeyEZgNQ63bdqGxJOkX2f2OzIOWwn8lYBDX36bqRwbeH47sp851VH01fPiBcoLZOPuhCHudW8Pfky8TtmOYYXL4aOkvx5FdjlFEOWxEKZD3lnfGJ+v7mZPErfjUEidvbp2AD8J/3tGDKYkZCgFPCJRFu8vbf2UjLL491uujF1tU5vGbq4LDLd2lwX1NbEfUWsIFOp50S8jqEnrP+fz+cMxzOs4TV4HITNqln/15uqqddEVNTP7Ax0jjMuV+rXAwLrzxVgP+/B4k7bb9PM4y2l17XRuctg4t84kcZL/EtUBlTliv2oGc2bJMfyS9szkaQPVR5WROS/+cKg0QKtxUCWcIwnW4RBo1j4NbwVHA2mLYjHW3QnPL1P+i5w4AeScRfCjB3p7wqdxREMuKNhIMsJAachsm2dUhBV68xI5cZnfI+CRK+Xmbpn8Oqy0y3pJLHulPlOATPI3v3LQ3cgZnzYTkN8xUdEB5N18vgN9QSTfjbxLSCZMNt2F4jWU4ncTVOnj5GRM+I0dt3SR2wkNyF+f9iP3/qF6M5mZ/ZIykFPF9RhONTyOxGeL2/UKIecGzQSV0XcP4rPsG6BGj3DavDVwRkGORoNTv5dPNtrQd/CQeL+ug/dckz9J32kq/Hw9Ys1xjh/ujv5oy18W2e+bS4Ir1Po+bJTr6ysGLUIqqB9gP4vVH1RPXsC9o3lVFSrxAOlbPN/kskCHHU3OWROl3q8xsWmDbH6VIZdLkNzEysG4wIJBtjLsxpAZvQB3+rNUguUBrjhHMzMT4rhb196T59vMNWEPhs1SF/cECwF78gBI2Oa29vZevnL7vFNVkoaCDVYLPh9Euoo3pYToz0rXZmVMT53Ot55cakJfFsXZJJk/LIozYlyGAIDKgGWusgv+W3b5jqVmZDN5GX2GFncTJ0Evme+sJXdGcM0pH1m0CdXcIRfSUQqub/P+tPLdOd23NAuDidtuKx3ZpoZMxPPdnIejgzy7kFcErhUg44a7s1/bI1idH2EsOHMIjJ4EVL5Be7xqmGniYeKeA/LrTDepLQWJmvXNRSuuh2IjEms/2jH9P4T1qpXQi/8p8jW6RobphU/efwoyRA5VhOs2CoZcPNY3e3HtKNk/VSWhniwAHVXXjjFvdPjdsxCsqM6/Qw/DD+cNm377aMB2DziGTROJR0OxStpS4f7yWOjsLEFLINL39dtgeOP8n/JpMPKlwMV6Y6pDFCZLTQPewAC2yK1DHNrCuGCWfMM7EHBsomsWqmXbR86AHVGCW3U0a5X9QY/nPjEOUzfZ/uro8YouMQA7LvwsIkHaSRszsSM1t3ubjkFEmtIpyPtOrKxc/uMmYJr6BgdVzCx4gy0UAoWTwEWc+V1MebuBraJ3+6hOSGLkhCrq8De2EoJGedXOVdZllMKUZBUVLltChB3ixqRjUCBjcadlbLtI7ujnA+B/Frzj5Kr8iuFUEZ2z99UkZniH7MEJvHftGX4ystOrNjng6S5/DxtDOBRCr2c7/RMngg7ioYxr41+8E1whAAv6a99cUIdpto+gY/cUxVz+E1icbO8Fa6VxwcEY4VmuAKZUqENJrBH5kAPmdk/ZlN/7Phar6IFBcqX7+kRMBoUq7FQ5U1/+E0oYUG1WSvYZ5/hCmd3/bGIEWfXf7NuOu+CQoA1Jn9nEvhgWePg9VxTm/PI2ziCLMdhR4Ckx6XE3NGny7z5kTd7h73KIQfip3W8UYfI4YLfA03yTWd/802FDpMzaShuHpV45SA1o/n41S/dWmoE7aP2aV92lV9jJKKSwHNJPuTfBXL4NhfVcqhrKRcSkT9W3vFwDtjgPx02PzKLF0iVGg/SdZZw4VVQOgfulMNg+TGdlVVduGSE8D7xGUMXPxJkmm3YbmN2bbsQkZxiu6xFGSMpkIolvK/JYItgh/vQtF6tEAFIw9ySKPwEm4QjiVWEJq0Z0JEMT6Da7ATRAMeoItYy+/O3Y1GukwTiVwiTr3ZnvDZ2eZkvvKIRin2H8ncpFbYBOVLFjxixlUkgVNBWaYITp/Rl5QhX2+3U124x5uRDCifkoMqzyTXVruY/VlniFY3fT2pQChAg1qD93dKr/glMksBXSXoHa7VsVTrp0DN4m7oVREsrjZrDZ2SvcchCVfb96B+ajSMUKyxNUGH8Rm9YZdN40LCh2QTAb3WyENxOzdbEL1/Wzmve9PoSHV7+Ic8e4HpiO2Vh6Yg/d45YRdVIl9oU/ICAakG0Tee/TKXyeZenu2QCtjTxY/m7WmycWw1aKdk9uOiFy4/xETUo9X4rifAceLUh5stsyj1SEj+dm4dv5x3FtFB1z166UZrHkzqMO8V+YO1/NeU/UZUiClIqgyRELGbDlPfNtzvvibOL7XCj9bz9GEnrjinlu4HHYDkKEC2reSmePKv4w2c0/bhSH6ZPb7Dx/MlxbUid5MFjzLuoZw6ODP/QO7NTlLDNnz5sGbMShg0prad8BqSiiDBKnkq5Z+N0igci8TVtkgPjixehLFNKxYYleEWEidD3+eGSa8nCAJRgegqhKjdIlNs16WbHaf7aQcdPsEH8POAP7X8kAtON5R8kecig4FL/bJmVcPVcrCJ3sGomFDFQ2OtUSVCuX067SpC1/jNC9fdd8i5540jh1FY6QLdqa4tZciEW8Qvj6qAR2nrKtF/l2ePZM+TDD1PsvmqDJALZaogXcxT+Lr6miZ4JUroHwVr1PFFBCKU5BU9OEZp5m7fkjqhlatfkZosAJbKHLDQ14K2F8x4c+wI+vXRlVLdxn14NVX8tyRz9vPogK8NBoYPvoyzH3bAMgpakfw8ldzH5QfugVFNw7yghmzYU6iz7xNMyn8aKr5nFWSxIE8VLdKTd8dsHMyDc6z7t3YcqRBRBilmKyMYhtXeJvpol6hLi9EhdS67kEJWCp4e4qakQgXmvfHgAihAslTFcscNQJqc82G40qm3qd0n70859AOimMPKJdcJA8v/q3hhqkp5qZfFQS+XJnFzd1u/+bI2OQ1ppVY5Cyhz1nSpHvvld5YHFSpSLm2IKGhs7QxlKbybBkPFTbNhnKJ99rryW71yypFfyHs/b8jDWIMcGKs4X1artl8dkFWFrM6ffxrr13yvv4jCeFlBbB/IZs5eEQ65Gyb9JCs1f5gtFI38+6KbaYpgauVga710hnt77OpX0aDuO7oJB/LYvQw1nJPIbYEgybg+AuQXv0G6qs+7DnCQPJhp+8Qaf2KaP5wRNH5fPieI134kgUkCA1xm9tGX44w529PC9y5XH1DY1ShdMTA5ak+HbXeZKObb5m/4j6Gz2lHbuzFiufzrvzpUaFiB7RrYWstnFbVhe2q1J0DL47fn+zZtLqRrOhQDAMuyzn8WN1Radn9zWEYh0aAA+7mpqMc+hbSMN4JxyTycRlFR4aaLJVReZOtErMASXD+aw2ZNF8iVTrmTn/cJ5upi13u8ERgo8pLZXN8WfSUpLLHUcdqMy4f5TveNfs995YmmH4QiTb9/E3Pdjoz76nk68KOWCzO8GGeYKeaPl4LVzatfhJnUoLb22GoPM4FfyyMwcDdQrAEVV6pYBqOJzxThmpMcyGSZVszYbYbTTigquoC1+k/MRvnl9lkycLMy5/DxcLd/syN/7U4lRWsaBu24nN9tNl5SdGr4O3tGYquMcD92ONRzJV3meiQGzIym1KB5jyPbg4U0kBNsdVFPkns7WrUIL516+g6E7xpDtkAMM2VEvJ8p6/xhkSYKz9ckr5mA9hYdOHWHM08V2YbCXpyoQ53TfjO+AQ8XhpvREslrpvbtftf1LoZ/GtE5BR3s3Ge4M99H3x907nr/KD692pqspr+EN9kevTGmKC2C1b0mEm3T/OfGfmg5eTxb9qqPYIuJDOTzwexikxRVkzsL/1iPhzpvZOJ1FRZnJCbB6ih2wBgaFSVO3w5STgkOWcovFT9nmI4hqKaC+2Iv4Ta/tvcJA7i/9zADiY5GecQZplPn3yVlud+gPc/AnVeiiidqxcvwJzY1J+OlaQLGpIy8mD8LAH0DwgaWpvi6++rEeUc4Up9AtnQ/nCMWGfqTu9Cc8cAfhRwbmND7wuS3uXdhOp7XLDlph0cxfzISzZ8jrYJFYA3V0R5V6kcZt05DNqeRLZo7SIjgJWMHmO8Nt+6gt79AjfEUag+SxsXdjGBUnhoYLEWpu9FODmQ3PKDOgDEk4IBm0QafRb7Yy/m+8M6EkokxOJedavgVuoG226j+IKqAea2nTEZeqvLsaq/vdcHqoIHAh33cWTyCEdNt/GHTNYgI/4mcaudHoUshLAUko585PcbrfRjVsGuFaCQygfykwiP7wFpn4NXLZL/qiCh8jszEOquW3Pw092I9YpPuOafPjAUvKyJoYuQc9myPdRiqAOxrsvdeun49+vwjiOWdiSFLITXKFFBWFjCwN5FGnuh5t9aTWHGuTxoKi9rFIunERkjPpkJHZgVXrkbmrMpG92vllKXx+9DlWtU3vMaETPmiD15HLw50XQ23UxK1jEyQq250ehM3dTUxqujD90AbXgX63tu/8XT/AFJ+2gAB09Qdxa/ekl5O12krf349QcjD5N6ZRJaQ7QjTh3qetl9Wnk2ACYEomNHcVRf1Pc99QQTirc2ZvXtIFl/w0Wrci8c2/PfyYzaRrNC8tD2V2B4XZcV+2bmyR7YFvFPi2Jvu/lirwsR+0qObi158Af9rhEF4pnXtl2IUXkq03i1M3z9GohdgG3v/KNHOY96jhRpnQlQmKoxWANF7/Za/t2Vs7zcyDUbzgW+ZQavB9Dfg/ZRU36xQLoGmetN5xSuCRZJ2yBXKLMC5kcsd+LDhRxjeGfls5h9qy5p5Sxq53CCLCTRAnSKy2z5GFez1bjkm8pmEIyADeIT8UZHTC8eW9bsJzs6odjUvPHNh7nxUKneAQ8NuXMgJzLHzmtiMTPEjwtydH4TejhPrbqcRfOwqB7wYukWI2DI/+oBW5kObQtWqt+lGv1fJoB2R4jBV8F8DHDJTEc0oN4tnkxBE5byybiYTFN8tL8FfZ2wYz7apAUVMHysyAgi8z01LkNXLsvHK8f9ZlylLYlWCviLU5+gKEo4BgMaenJJp7ak8Id2+fIjiTYEuZHAXVbzokxNt0PCetjIyjQk61C3whpV7HcEEmSYkeOGFU+P9RmjWukJuRRlJNrsBH55UNJqtD8Z5fvW92y0HBYi2wFq2de3w5h4p8/dQF3Vz6CEhFeT9OfNEDEP1j1SYjP8vcZgoAw6eVXQa2ekVXnR2Ze1WxZa+f6sji4LEX5ffWBplWckW9Fz5uESCxORMDlQq7m+A4BgL+SWbBYuSV1lgkXRBrlNNHl6yTJqtxsStG16QTT3cjwHjTfQMWMzMmQT/JGab01VqDgPgrsMTKLkyHcIXIY18HGSF32kvQjRWOvwCLHeljmdOSih5VClxHes8fORavnvEtKgzoRKP0Hqn3ADaySWVxVFAmcduSAA1NeyN2jRLnpmxkgHEPIRzXTUYoknlAFk2VX76bqZlnyRYmcakm6/5ekX1+5YOj6A2mEeJ8ALdCBLF518pQ1ic8NQj/yyP3IXAIUUdyg1D4yt8gVM7ANEvsAvXMc2QE07wuASSceiFkgfcqRz+hXMgpvXAJzjH7PuggjgMS8dCM3stsQXLi/8OEaApTHaXs1kbwzOAm4k66yN2tmfjtob+VHxEy0IMNqayi1NfIepdw8GmivvzNBC4ognBrKxdTfG197bfvaICoj+DKy//eWAk5dV7CIwxAaL2w8ElvphAZBAL2Bk58Mb/4cBwu4gto+B18juKCNoudoQhdy/ghBGIZYnMdxvRoMOevIhCIea9bQdI+dUhRSfv4JInEvsAsVoC4xx2z+r8d6BWVXOKnOshTvY1bjjw14PnyD8rMPogbCnYlRLQp1r5As2Q2AgPXQnAF64Mwvde/exrK6tNbfqwVrDcTpLwTQh8KILnFyTNjnD9NGX2Rg/44TdEiou8AuLZvM2WEqHinB1SqZNQL8Q5gbebuMyARAJmCtRWIC1N41mPSfMkpCdEIKXwVtWi+uM/c4lSOEeX0hDA/SNOfC06KDFs9NGV2nnYe4yRKixT5Gi63u8rtOUND6vm/c6xJP0hMtv7OJ5fgsC+Tg+NEUM2nsFX0UoJge0h4cmGiPk/wt4zXJkop+OTOjaE0PlElzpIlXL5U6q/hDWKqHz1F7cxYGThO1d0m9ncG0bugTNosf66DfTTY4F2vcZcB/t5UzDpNfiSK4g3y6us5DGj5P1fJUxr//3vVPo0/mQY08rAtv3k1dn955r+/df6bTJ9MHse4AEDRBAyegm++UShja6vT/bsHv7R9wWSfW1mo0oZY/goU+IFrcA0opNSWsfx5Hcrml5hGsnUJnoOWut3Hp8CiBoYHyO05WA74CukPkc+am78KPlqsIyp/RH7bcLKU/C26HWyN3tC+7WRItFYy8La6DUFsuOlhfFu1G9YQ8UcWheaFqv32E89To0tMzwrBQf4dFJxw+ysdEjqpO+I7OY+gMYYSh6X4Kr4SkQv6XbL8/YvzgGw9LU1z42tLF6WrJhDTx78KxhO/fUo49umgTJjV72QB10zugCJ71mju7f0NqQKbzXkkevkFgjzhhwAaOCmR9a9znBH5oHnbW+Hiojxg9Xs8Y5uSJ8go1X8JG78ymFZDlZZCOLKim/88dqPTkH+4r54SK+2uqIOk1R2A5sESKzRECDVIt2v0wG25wACpsI0JmEN8MC0xHbNqzHK9AM6UZP0rYi+7dsJrO1sudmCwYUkRkQ7LiBrz40d4EeY03njArpknh/PdSlLj/fV7jn5DfYjyw0lIBlix5nTKmuwV2XjcyPZhygsxL81PQ1fLE2pmWstpRHwouB3DOLFqohd9y4qLFAT40stbv/hePjU7hoZUJRtRerER93UIF8FVc4h2oNns9+PuuUQ9dAi4ltE+5aXJ4bQag4CH/sFKGyoDSXiq2i5vu2viNV/jCN7Y4rOAXO1OPgNn+vp1zv/UaaSSsag/P7Np3sAUQLn1UZkBbsWdbQiQUeWa+P2rG2GRDmJ7HBJ2enGQaBZS+PK1M/1FBYbdOD6Ug/wtxWWuKb5dliomoSm4A6cOdA1Ku3rOkL5m78Uj5r1ShBfdZgIGl0ooScg5gwA5G0be7uW+MXhC1/rF3fPV65S2FqWNHWn9yS+Luu/o2SFVUj/3FC8rJtZYf8QSJ3Qz+E8hbBaLgRzbSHiQNuN4vgAQyC0wjstUCToIrCTumEqFwUmSEQXyV4J+bNWAa1cDphWsDyhagJ80wbR/oknNmKK/AqSBAAAAAA==', rating: 4.8 },
    { id: 2, name: 'Vở Hồng Hà 200 Trang', brand: 'Hồng Hà', category: 'sach-vo', price: 25000, oldPrice: 30000, image: 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400&h=400&fit=crop', rating: 4.7 },
    { id: 3, name: 'Bút Chì Màu Faber-Castell 24 Màu', brand: 'Faber-Castell', category: 'do-dung-ve', price: 89000, oldPrice: 120000, image: 'https://images.unsplash.com/photo-1513475382585-d06e58bcb0e0?w=400&h=400&fit=crop', rating: 4.9 },
    { id: 4, name: 'Balo Jansport SuperBreak', brand: 'Jansport', category: 'balo-cap', price: 890000, oldPrice: 1200000, image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=400&fit=crop', rating: 4.6 },
    { id: 5, name: 'Máy Tính Casio FX-580VN X', brand: 'Casio', category: 'may-tinh', price: 590000, oldPrice: 750000, image: 'data:image/jpeg;base64,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', rating: 4.8 },
    { id: 6, name: 'Đèn Bàn Học LED Điều Chỉnh', brand: 'Philips', category: 'den-hoc', price: 450000, oldPrice: 600000, image: 'https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=400&h=400&fit=crop', rating: 4.7 },
    { id: 7, name: 'Bút Máy Thiên Long TL-079', brand: 'Thiên Long', category: 'van-phong-pham', price: 45000, oldPrice: 60000, image: 'https://images.unsplash.com/photo-1561070791-2526d30994b5?w=400&h=400&fit=crop', rating: 4.6 },
    { id: 8, name: 'Vở Campus 120 Trang', brand: 'Campus', category: 'sach-vo', price: 18000, oldPrice: 25000, image: 'https://images.unsplash.com/photo-1503676260728-1c00da094a0b?w=400&h=400&fit=crop', rating: 4.5 },
    { id: 9, name: 'Bút Chì 2B Staedtler', brand: 'Staedtler', category: 'van-phong-pham', price: 12000, oldPrice: 15000, image: 'data:image/jpeg;base64,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', rating: 4.7 },
    { id: 10, name: 'Thước Kẻ 30cm Nhựa', brand: 'Thiên Long', category: 'van-phong-pham', price: 8000, oldPrice: 12000, image: 'https://vanphongpham123.com/pic/products/thuoc-ke-_637740492089870680_HasThumb.jpg', rating: 4.4 },
    { id: 11, name: 'Tẩy Gôm Pentel', brand: 'Pentel', category: 'van-phong-pham', price: 15000, oldPrice: 20000, image: 'https://cdn1.fahasa.com/media/catalog/product/4/0/4007817523865-1.jpg', rating: 4.6 },
    { id: 12, name: 'Bộ Màu Nước 12 Màu', brand: 'Faber-Castell', category: 'do-dung-ve', price: 125000, oldPrice: 180000, image: 'https://images.unsplash.com/photo-1513475382585-d06e58bcb0e0?w=400&h=400&fit=crop', rating: 4.8 },
    { id: 13, name: 'Giấy Vẽ A4 200 Tờ', brand: 'Double A', category: 'do-dung-ve', price: 45000, oldPrice: 60000, image: 'https://images.unsplash.com/photo-1503676260728-1c00da094a0b?w=400&h=400&fit=crop', rating: 4.5 },
    { id: 14, name: 'Cặp Sách Học Sinh', brand: 'Hồng Hà', category: 'balo-cap', price: 350000, oldPrice: 450000, image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=400&fit=crop', rating: 4.6 },
    { id: 15, name: 'Balo Nike Heritage', brand: 'Nike', category: 'balo-cap', price: 1200000, oldPrice: 1500000, image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&h=400&fit=crop', rating: 4.7 },
    { id: 18, name: 'Đèn Bàn Học Chống Cận', brand: 'Sunny', category: 'den-hoc', price: 320000, oldPrice: 450000, image: 'https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=400&h=400&fit=crop', rating: 4.5 },
    { id: 20, name: 'Bộ Bút Lông Màu 36 Màu', brand: 'Faber-Castell', category: 'do-dung-ve', price: 180000, oldPrice: 250000, image: 'https://images.unsplash.com/photo-1513475382585-d06e58bcb0e0?w=400&h=400&fit=crop', rating: 4.8 },
    { id: 21, name: 'Balo đựng mèo', brand: 'Thiên Long', category: 'balo-cap', price: 330000, oldPrice: 380000, image: 'https://cunsieupham.com/wp-content/uploads/2023/06/22584100670_1106168684.jpg', rating: 4.8 },
    { id: 22, name: 'Máy Tính Casio FX-570VN Plus', brand: 'Casio', category: 'may-tinh', price: 490000, oldPrice: 650000, image: 'https://bizweb.dktcdn.net/100/379/648/products/may…plus-8-x-16-cm-l-1537255666-2.jpg?v=1587370028027',rating: 4.7},
    { id: 23, name: 'Máy Tính Casio DF-120 ', brand: 'Casio', category: 'may-tinh', price: 250000, oldPrice: 300000, image: 'https://bizweb.dktcdn.net/100/379/648/products/may…plus-8-x-16-cm-l-1537255666-2.jpg?v=1587370028027',rating: 4.7},
    { id: 24, name: 'Máy Tính Casio FX-880 ', brand: 'Casio', category: 'may-tinh', price: 800000, oldPrice: 900000, image: 'https://cdn1.fahasa.com/media/catalog/product/4/5/4549526613708.jpg',rating: 4.9},
    { id: 25, name: 'Máy Tính Casio FX-570ES Plus ', brand: 'Casio', category: 'may-tinh', price: 430000, oldPrice: 600000, image: 'https://www.bachdang.info/image/cache/catalog/revs…lder/6361ddff80f0dde2fd7141fb6f5772f9-500x524.jpg',rating: 4.8}
];

let currentPage = 1;
const productsPerPage = 9;
let filteredProducts = [...allProducts];

// Get URL parameters
function getUrlParams() {
    const params = new URLSearchParams(window.location.search);
    return {
        search: params.get('search') || '',
        category: params.get('category') || '',
        page: parseInt(params.get('page')) || 1
    };
}

// Apply filters
function applyFilters() {
    const urlParams = getUrlParams();
    const searchTerm = urlParams.search.toLowerCase() || document.getElementById('searchInput')?.value.toLowerCase() || '';
    const maxPrice = parseInt(document.getElementById('priceRange').value);

    // Get selected categories
    const selectedCategories = [];
    document.querySelectorAll('#sidebar input[type="checkbox"][id^="cat-"]:checked').forEach(checkbox => {
        selectedCategories.push(checkbox.value);
    });

    // Get selected brands
    const selectedBrands = [];
    document.querySelectorAll('#sidebar input[type="checkbox"][id^="brand-"]:checked').forEach(checkbox => {
        selectedBrands.push(checkbox.value);
    });

    // Filter products
    filteredProducts = allProducts.filter(product => {
        // Search filter
        const matchesSearch = !searchTerm ||
            product.name.toLowerCase().includes(searchTerm) ||
            product.brand.toLowerCase().includes(searchTerm);

        // Price filter
        const matchesPrice = product.price <= maxPrice;

        // Category filter - ưu tiên URL param, sau đó mới đến checkbox
        let matchesCategory = true;
        if (urlParams.category) {
            // Nếu có category trong URL, chỉ hiển thị sản phẩm thuộc category đó
            matchesCategory = product.category === urlParams.category;
        } else if (selectedCategories.length > 0) {
            // Nếu không có URL param nhưng có checkbox được chọn, lọc theo checkbox
            matchesCategory = selectedCategories.includes(product.category);
        }
        // Nếu không có cả URL param và checkbox, hiển thị tất cả (matchesCategory = true)

        // Brand filter
        const matchesBrand = selectedBrands.length === 0 ||
            selectedBrands.includes(product.brand);

        return matchesSearch && matchesPrice && matchesCategory && matchesBrand;
    });
    // Apply sorting
    const sortValue = document.getElementById('sortSelect').value;
    switch(sortValue) {
        case 'price-asc':
            filteredProducts.sort((a, b) => a.price - b.price);
            break;
        case 'price-desc':
            filteredProducts.sort((a, b) => b.price - a.price);
            break;
        case 'name-asc':
            filteredProducts.sort((a, b) => a.name.localeCompare(b.name));
            break;
        case 'name-desc':
            filteredProducts.sort((a, b) => b.name.localeCompare(a.name));
            break;
        case 'rating-desc':
            filteredProducts.sort((a, b) => b.rating - a.rating);
            break;
    }
    currentPage = 1;
    renderProducts();
    renderPagination();
    updateProductsCount();
}
// Reset filters
function resetFilters() {
    document.getElementById('priceRange').value = 5000000;
    document.getElementById('maxPrice').textContent = '5.000.000đ';
    document.querySelectorAll('#sidebar input[type="checkbox"]').forEach(checkbox => {
        checkbox.checked = false;
    });
    document.getElementById('sortSelect').value = 'default';
    document.getElementById('searchInput').value = '';

    // Clear URL params
    window.history.pushState({}, '', 'products.html');

    applyFilters();
}
// Render products
function renderProducts() {
    const container = document.getElementById('productsGrid');
    const startIndex = (currentPage - 1) * productsPerPage;
    const endIndex = startIndex + productsPerPage;
    const productsToShow = filteredProducts.slice(startIndex, endIndex);

    if (productsToShow.length === 0) {
        container.innerHTML = '<p style="grid-column: 1/-1; text-align: center; padding: 3rem; color: var(--text-light);">Không tìm thấy sản phẩm nào.</p>';
        return;
    }
    container.innerHTML = productsToShow.map(product => `
        <a href="product-detail.html?id=${product.id}" class="product-card">
            <img src="${product.image}" alt="${product.name}" class="product-image" onerror="this.src='https://via.placeholder.com/400'">
            <div class="product-info">
                <h3 class="product-name">${product.name}</h3>
                <p class="product-brand">${product.brand}</p>
                <div>
                    <span class="product-price">${product.price.toLocaleString('vi-VN')}đ</span>
                    ${product.oldPrice ? `<span class="product-price-old">${product.oldPrice.toLocaleString('vi-VN')}đ</span>` : ''}
                </div>
                <div class="product-rating">
                    <span class="stars">${'★'.repeat(Math.floor(product.rating))}${'☆'.repeat(5 - Math.floor(product.rating))}</span>
                    <span>(${product.rating})</span>
                </div>
            </div>
        </a>
    `).join('');
}
// Render pagination
function renderPagination() {
    const container = document.getElementById('pagination');
    const totalPages = Math.ceil(filteredProducts.length / productsPerPage);
    if (totalPages <= 1) {
        container.innerHTML = '';
        return;
    }
    let paginationHTML = '';
    // Previous button
    paginationHTML += `<button onclick="goToPage(${currentPage - 1})" ${currentPage === 1 ? 'disabled' : ''}>‹ Trước</button>`;
    // Page numbers
    for (let i = 1; i <= totalPages; i++) {
        if (i === 1 || i === totalPages || (i >= currentPage - 2 && i <= currentPage + 2)) {
            paginationHTML += `<button class="${i === currentPage ? 'active' : ''}" onclick="goToPage(${i})">${i}</button>`;
        } else if (i === currentPage - 3 || i === currentPage + 3) {
            paginationHTML += `<span>...</span>`;
        }
    }
    // Next button
    paginationHTML += `<button onclick="goToPage(${currentPage + 1})" ${currentPage === totalPages ? 'disabled' : ''}>Sau ›</button>`;

    container.innerHTML = paginationHTML;
}
// Go to page
function goToPage(page) {
    const totalPages = Math.ceil(filteredProducts.length / productsPerPage);
    if (page < 1 || page > totalPages) return;
    currentPage = page;
    renderProducts();
    renderPagination();
    // Scroll to top
    window.scrollTo({ top: 0, behavior: 'smooth' });
    // Update URL
    const url = new URL(window.location);
    url.searchParams.set('page', page);
    window.history.pushState({}, '', url);
}
// Update products count
function updateProductsCount() {
    const showingCount = Math.min(currentPage * productsPerPage, filteredProducts.length);
    const startCount = (currentPage - 1) * productsPerPage + 1;

    document.getElementById('showingCount').textContent =
        filteredProducts.length > 0 ? `${startCount}-${showingCount}` : '0';
    document.getElementById('totalCount').textContent = filteredProducts.length;
}
// Initialize
document.addEventListener('DOMContentLoaded', function() {
    const urlParams = getUrlParams();

    // Set search input if URL has search param
    if (urlParams.search && document.getElementById('searchInput')) {
        document.getElementById('searchInput').value = urlParams.search;
    }
    // Set category checkbox if URL has category param - PHẢI SET TRƯỚC KHI GỌI applyFilters()
    if (urlParams.category) {
        const categoryCheckbox = document.getElementById(`cat-${urlParams.category}`);
        if (categoryCheckbox) {
            categoryCheckbox.checked = true;
        }
    }
    // Set page from URL
    if (urlParams.page) {
        currentPage = urlParams.page;
    }
    // Gọi applyFilters() sau khi đã set tất cả các giá trị từ URL
    applyFilters();
});
// Add sidebar ID for easier selection
document.addEventListener('DOMContentLoaded', function() {
    const sidebar = document.querySelector('.sidebar');
    if (sidebar) {
        sidebar.id = 'sidebar';
    }
});


