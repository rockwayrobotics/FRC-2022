'''Proof of concept for retroreflective tape detection.'''

import itertools
from pathlib import Path
import re

import numpy as np
import cv2 as cv

ASSETS = Path(__package__) / 'assets'
# print('assets are in', ASSETS.resolve())


def green(img, low=70, high=90):
    hsv = cv.cvtColor(img, cv.COLOR_BGR2HSV)
    mask = cv.inRange(hsv, (low, 100, 25), (high, 255, 255))
    imask = mask>0
    green = np.zeros_like(img, np.uint8)
    green[imask] = img[imask]
    return green


def show(img):
    try:
        cv.imshow('green', img)
        c = cv.waitKey()
    except Exception as ex:
        print('well, that didn\'t work: %s' % ex)
    finally:
        cv.destroyAllWindows()

    return c


def aspect_ratio(cnt):
    ((x, y), (w, h), angle) = cv.minAreaRect(cnt)
    return max(w/h, h/w)


def showContours(im, contours, area=(150, 1000), ar=(2, 5), name=''):
    sized = [c for c in contours if area[0] < cv.contourArea(c) < area[1]]
    if sized:
        print('contours', '\n'.join(f'{i}={cv.contourArea(c)} @{aspect_ratio(c)}' for i, c in enumerate(sized)))
    else:
        print('no contours found')
    rects = [c for c in sized if ar[0] < aspect_ratio(c) < ar[1]]
    imx = cv.drawContours(im.copy(), rects, -1, (0, 0, 255), 3)
    cv.putText(imx, f'Found {len(rects)} candidate{"s" if len(rects) != 1 else ""}.', (10, 60), cv.FONT_HERSHEY_SIMPLEX, fontScale=0.8, color=(0, 255, 0), thickness=2)
    if name:
        cv.putText(imx, name, (10, 30), cv.FONT_HERSHEY_SIMPLEX, fontScale=0.8, color=(0, 255, 0), thickness=1)
    return imx, {'count': len(rects)}


def filter(path, area=(150, 1000), ar=(2, 5)):
    path = str(path)    # cv doesn't know about Path
    print('-' * 30)
    print(path)
    im = cv.imread(path)
    im_g = green(im)
    im_bw = np.mean(im_g[:,:,0:2], axis=2)
    ret, thresh = cv.threshold(im_bw, 100, 255, 0)
    contours, hierarchy = cv.findContours(thresh.astype(np.uint8), cv.RETR_TREE, cv.CHAIN_APPROX_SIMPLE)
    return showContours(im, contours, area=area, ar=ar, name=path)


if __name__ == '__main__':
    import argparse
    parser = argparse.ArgumentParser()
    parser.add_argument('paths', default=['2022VisionSampleImages'], nargs='*')
    args = parser.parse_args()

    print()
    print('Use f or j to move forward, b or k to move backward, q or ESC to quit.')
    print()

    def sortnums(x):
        '''Return a sorting key that compares digits numerically.'''
        def trynum(y):
            try:
                return int(y)
            except ValueError:
                return y
        return tuple(trynum(y) for y in re.split(r'(\d+)', str(x)))

    paths = sorted(list(*itertools.chain((ASSETS / p).glob('*.png') for p in args.paths)),
        key=sortnums)
    index = 0
    while True:
        p = paths[index % len(paths)]
        im, info = filter(str(p), area=(100, 1000))
        c = show(im)
        # print('c', c, chr(c))
        if c in (ord('f'), ord('j')):   # go forward
            index += 1
        elif c in (ord('b'), ord('k')): # go backwards
            index -= 1
        elif c in (27, ord('q')): # quit (27 == ESC)
            break

# show(green(cv.imread('upper-hub-1.png')))
# show(green(cv.imread('2022VisionSampleImages/TarmacCenter3ft10in.png')))
# im2 = cv.imread('2022VisionSampleImages/NearLaunchpad8ft10in.png')
# show(im2)
# im_g = green(im2)
# show(im_g)
# im_bw = np.mean(im_g[:,:,0:2], axis=2)
# show(im_bw)
# ret, thresh = cv.threshold(im_bw, 100, 255, 0)
# show(thresh)
# contours, hierarchy = cv.findContours(thresh.astype(np.uint8), cv.RETR_TREE, cv.CHAIN_APPROX_SIMPLE)
# from random import randint as ri
# randc = lambda: (ri(0, 255), ri(0, 255), ri(0, 255))
